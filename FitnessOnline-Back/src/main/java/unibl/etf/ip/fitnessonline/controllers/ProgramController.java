package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.fitnessonline.model.dto.JwtUser;
import unibl.etf.ip.fitnessonline.model.dto.ProgramDTO;
import unibl.etf.ip.fitnessonline.model.dto.ProgramDTOPage;
import unibl.etf.ip.fitnessonline.services.CategoryService;
import unibl.etf.ip.fitnessonline.services.ProgramService;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;
    private final CategoryService categoryService;

    public ProgramController(ProgramService programService, CategoryService categoryService) {
        this.programService = programService;
        this.categoryService = categoryService;
    }

    @GetMapping("/creator")
    public ResponseEntity<ProgramDTOPage> getByCreator(@AuthenticationPrincipal JwtUser jwtUser, @RequestParam(defaultValue = "0") int pageNo,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(programService.getProgramsByCreator(jwtUser.getId(), PageRequest.of(pageNo, pageSize)), HttpStatus.OK);
    }

    // ova metoda (endpoint) vjerovatno nije potrebna
    @GetMapping
    public ResponseEntity<ProgramDTOPage>  getAll(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<>(programService.getAllPrograms(PageRequest.of(pageNo,pageSize)), HttpStatus.OK);
    }

    @GetMapping("/active/{p1}/{p2}/{valid}/{name}/{category}/{difficulty}")
    public ResponseEntity<ProgramDTOPage>  getFiltered(@PathVariable("p1") double p1, @PathVariable("p2") double p2, @PathVariable("valid") String valid, @PathVariable("name") String name, @PathVariable("category") String category,
                                                       @PathVariable("difficulty") int difficulty, @AuthenticationPrincipal JwtUser jwtUser,
                                                       @RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<>(programService.getFiltered(p1,p2,valid,category,name, difficulty, jwtUser, PageRequest.of(pageNo,pageSize)), HttpStatus.OK);
    }
    @GetMapping("/creator/{p1}/{p2}/{valid}/{name}/{category}/{difficulty}")
    public ResponseEntity<ProgramDTOPage>  getFilteredByCreator(@PathVariable("p1") double p1, @PathVariable("p2") double p2, @PathVariable("valid") String valid, @PathVariable("name") String name, @PathVariable("category") String category,
                                                                @PathVariable("difficulty") int difficulty, @AuthenticationPrincipal JwtUser jwtUser,
                                                                @RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<>(programService.getFilteredByCreator(p1,p2,valid,category,name,difficulty, jwtUser, PageRequest.of(pageNo,pageSize)), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id, @AuthenticationPrincipal JwtUser jwtUser){
        boolean flag=programService.delete(id,jwtUser.getId());
        if(flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/{rand}")
    public ResponseEntity<ProgramDTO> add(@RequestBody ProgramDTO programDTO, @PathVariable("rand") String rand){
        ProgramDTO result=programService.add(programDTO,rand);
        if(result!=null)
            return new ResponseEntity<>(result,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/participated/{id}")
    public boolean checkIfParticipated(@PathVariable long id, @AuthenticationPrincipal JwtUser jwtUser) {
        return programService.checkIfParticipated(id, jwtUser);
    }

    @GetMapping("/participations")
    public List<Long> getParticipations(@AuthenticationPrincipal JwtUser jwtUser) {
        return programService.getParticipations(jwtUser);
    }


}
