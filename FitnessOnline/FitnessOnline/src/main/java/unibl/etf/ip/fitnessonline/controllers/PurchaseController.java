package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.fitnessonline.model.dto.JwtUser;
import unibl.etf.ip.fitnessonline.model.dto.PurchaseDTO;
import unibl.etf.ip.fitnessonline.services.PurchaseService;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/buyer")
    public ResponseEntity<List<PurchaseDTO>> getByBuyer(@AuthenticationPrincipal JwtUser jwtUser){
        return new ResponseEntity<>(purchaseService.getByBuyer(jwtUser.getId()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PurchaseDTO> add(@RequestBody PurchaseDTO purchaseDTO){
        PurchaseDTO result=purchaseService.add(purchaseDTO);
        if(result!=null)
            return new ResponseEntity<>(result,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
