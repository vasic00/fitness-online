package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import unibl.etf.ip.fitnessonline.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> send(@RequestParam("recipient") long id, @RequestParam("content") String content, @RequestParam("file") MultipartFile file){
        try {
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes());
            emailService.sendMailFromAdvisor(content,id,byteArrayResource, file.getOriginalFilename());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
