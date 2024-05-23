package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.fitnessonline.model.Advisor;
import unibl.etf.ip.fitnessonline.services.AdvisorService;

import java.util.List;

@RestController
@RequestMapping("/advisor")
public class AdvisorController {
    private final AdvisorService advisorService;

    public AdvisorController(AdvisorService advisorService) {
        this.advisorService = advisorService;
    }

    @PostMapping("/login")
    public ResponseEntity<Advisor> login(@RequestParam("username") String username, @RequestParam("password") String password){
        Advisor temp=advisorService.login(username,password);
        if(temp!=null)
            return new ResponseEntity<>(temp, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<Advisor> openAdvisorAccount(@RequestBody Advisor advisor) {
        Advisor result = advisorService.openAdvisorAccount(advisor);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Advisor>> getAll() {
        return new ResponseEntity<>(advisorService.getAll(), HttpStatus.OK);
    }
}
