package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unibl.etf.ip.fitnessonline.services.ProgramImageService;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ProgramImageController {

    private final ProgramImageService programImageService;

    public ProgramImageController(ProgramImageService programImageService) {
        this.programImageService = programImageService;
    }

    @GetMapping(path = "{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id){
        byte[] image = programImageService.getImage(id);
        if(image!=null)
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        else
            return  ResponseEntity.notFound().build();
    }
    @PostMapping
    public void uploadImage(Model model, @RequestParam("image") MultipartFile file, @RequestParam("id") String id) throws IOException {
        System.out.println("Image upload hit! + id="+id);
        programImageService.saveImage(file.getBytes(),id);
    }

}
