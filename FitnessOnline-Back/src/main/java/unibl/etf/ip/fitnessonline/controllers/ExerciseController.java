package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.fitnessonline.model.dto.ExerciseDTO;
import unibl.etf.ip.fitnessonline.model.dto.JwtUser;
import unibl.etf.ip.fitnessonline.services.ExerciseService;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {

        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAllByUser(@AuthenticationPrincipal JwtUser jwtUser) {
        return new ResponseEntity<>(exerciseService.getAllByUser(jwtUser.getId()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> add(@RequestBody ExerciseDTO exerciseDTO, @AuthenticationPrincipal JwtUser jwtUser) {
        ExerciseDTO result = exerciseService.add(exerciseDTO, jwtUser.getId());
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
