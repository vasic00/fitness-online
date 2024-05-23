package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.fitnessonline.model.dto.CategoryDTO;
import unibl.etf.ip.fitnessonline.model.dto.JwtUser;
import unibl.etf.ip.fitnessonline.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final CategoryService categoryService;

    public SubscriptionController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllSubscribedCategories(@AuthenticationPrincipal JwtUser jwtUser) {
        return new ResponseEntity<>(categoryService.getAllSubscribedCategories(jwtUser.getId()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> add(@AuthenticationPrincipal JwtUser jwtUser, @RequestParam("categoryId") long categoryId) {
        CategoryDTO categoryDTO = categoryService.addSubscription(jwtUser.getId(), categoryId);
        if (categoryDTO != null)
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal JwtUser jwtUser, @PathVariable("categoryId") long categoryId) {
        boolean flag = categoryService.deleteSubscription(jwtUser.getId(), categoryId);
        if (flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
