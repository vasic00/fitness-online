package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.fitnessonline.model.dto.JwtUser;
import unibl.etf.ip.fitnessonline.model.dto.MessageDTO;
import unibl.etf.ip.fitnessonline.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllForAdvisors(){
        return new ResponseEntity<>(messageService.getAllForAdvisors(), HttpStatus.OK);
    }
    @GetMapping("/filtered")
    public ResponseEntity<List<MessageDTO>> getAllFilteredForAdvisors(@RequestParam("unread") String unread, @RequestParam("key") String key){
        return new ResponseEntity<>(messageService.getAllFilteredForAdvisors(unread, key), HttpStatus.OK);
    }
//    @GetMapping("/received")
//    public ResponseEntity<List<MessageDTO>> getAllReceivedByUser(@AuthenticationPrincipal JwtUser jwtUser) {
//        System.out.println("inside received controller");
//        return new ResponseEntity<>(messageService.getAllReceivedByUser(jwtUser.getId()), HttpStatus.OK);
//    }
    @GetMapping("/received/{unread}/{key}")
    public ResponseEntity<List<MessageDTO>> getAllReceivedByUser(@AuthenticationPrincipal JwtUser jwtUser, @PathVariable("unread") String unread, @PathVariable("key") String key) {
        return new ResponseEntity<>(messageService.getReceivedByUser(jwtUser.getId(), unread, key), HttpStatus.OK);
    }
    @PutMapping("/received/{id}")
    public ResponseEntity<?> readReceivedByUser(@AuthenticationPrincipal JwtUser jwtUser, @PathVariable("id") long id) {
        messageService.readReceivedByUser(jwtUser.getId(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/received/{id}")
    public ResponseEntity<?> deleteReceivedByUser(@AuthenticationPrincipal JwtUser jwtUser, @PathVariable("id") long id) {
        boolean flag = messageService.deleteReceivedByUser(jwtUser.getId(),id);
        if (flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> read(@PathVariable("id") long id){
        MessageDTO result = messageService.advisorRead(id);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        boolean flag = messageService.advisorDelete(id);
        if (flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<?> add(@RequestBody MessageDTO messageDTO){
        boolean flag=messageService.add(messageDTO);
        if(flag)
            return  new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
