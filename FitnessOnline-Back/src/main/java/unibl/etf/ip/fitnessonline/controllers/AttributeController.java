package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.fitnessonline.model.dto.AttributeDTO;
import unibl.etf.ip.fitnessonline.model.dto.SpecificAttributeDTO;
import unibl.etf.ip.fitnessonline.services.AttributeService;
import unibl.etf.ip.fitnessonline.services.SpecificAttributeService;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

    private final SpecificAttributeService specificAttributeService;
    private final AttributeService attributeService;

    public AttributeController(SpecificAttributeService specificAttributeService, AttributeService attributeService) {
        this.specificAttributeService = specificAttributeService;
        this.attributeService = attributeService;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<SpecificAttributeDTO>> getAllSpecificAttributesOfCategory(@PathVariable("id") long id) {
        List<SpecificAttributeDTO> result = specificAttributeService.getAllSpecificAttributesOfCategory(id);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<SpecificAttributeDTO> add(@RequestBody SpecificAttributeDTO specificAttributeDTO) {
        SpecificAttributeDTO result=specificAttributeService.add(specificAttributeDTO);
        if(result!=null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<SpecificAttributeDTO> update(@RequestBody SpecificAttributeDTO specificAttributeDTO) {
        SpecificAttributeDTO result = specificAttributeService.update(specificAttributeDTO);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        boolean flag = specificAttributeService.delete(id);
        if (flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/attribute")
    public ResponseEntity<AttributeDTO> add(@RequestBody AttributeDTO attributeDTO) {
        AttributeDTO result = attributeService.add(attributeDTO);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/attribute/{programId}/{specificAttributeId}")
    public ResponseEntity<?> delete(@PathVariable("programId") long programId, @PathVariable("specificAttributeId") long specificAttributeId) {
        attributeService.delete(programId, specificAttributeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
