package unibl.etf.ip.fitnessonline.controllers;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unibl.etf.ip.fitnessonline.model.Admin;
import unibl.etf.ip.fitnessonline.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<Admin> login(@RequestParam("username") String username, @RequestParam("password") String password){
        Admin temp=adminService.login(username,password);
        System.out.println("IN ADMIN CONTROLLER");
        if(temp!=null)
            return new ResponseEntity<>(temp, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
