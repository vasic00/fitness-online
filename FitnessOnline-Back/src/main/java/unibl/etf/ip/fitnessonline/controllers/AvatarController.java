package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.fitnessonline.services.AvatarService;

import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService){
        this.avatarService=avatarService;
    }
    @GetMapping
    public ResponseEntity<List<String>> getAllAvatars(){
            System.out.println("hit");
            ResponseEntity<List<String>> result = new ResponseEntity<>(avatarService.getAllAvatars(), HttpStatus.OK);
            return result;

    }

    @GetMapping(path = "{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getAvatar(@PathVariable("id") String avatar){
        System.out.println("hit "+avatar);
        byte[] image = avatarService.getAvatar(avatar);
        if(image!=null)
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
        else
            return  ResponseEntity.notFound().build();
    }
}
