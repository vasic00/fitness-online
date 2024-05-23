package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibl.etf.ip.fitnessonline.model.dto.CommentDTO;
import unibl.etf.ip.fitnessonline.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> add(@RequestBody CommentDTO commentDTO){
        CommentDTO result=commentService.add(commentDTO);
        if(result!=null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
