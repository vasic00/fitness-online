package unibl.etf.ip.fitnessonline.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import unibl.etf.ip.fitnessonline.model.dto.LoginResponse;
import unibl.etf.ip.fitnessonline.model.dto.UserDTO;
import unibl.etf.ip.fitnessonline.model.enums.AccountStatus;
import unibl.etf.ip.fitnessonline.model.requests.ActivationRequest;
import unibl.etf.ip.fitnessonline.model.requests.LoginRequest;
import unibl.etf.ip.fitnessonline.services.AuthenticationService;
import unibl.etf.ip.fitnessonline.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${front.activation.redirect}")
    private String activationRedirect;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request){
//        UserDTO userDTO=userService.login(request.getUsername(),request.getPassword());
//        if(userDTO==null)
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        else
//            return  new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
//    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        try{
            return  new ResponseEntity<LoginResponse>(authenticationService.login(request.getUsername(),request.getPassword(),true),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/activate")
    public RedirectView activate(@RequestParam("token") String token){
        System.out.println("inside activate controller");
        boolean flag=authenticationService.activateAccount(token);
        if(flag) {
            System.out.println("time to redirect");
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(activationRedirect);
            return redirectView;
        }
       return null;
    }
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody UserDTO user){
        System.out.println("hitting register controller");
        UserDTO userDTO=userService.register(user);
        if(userDTO!=null)
            try {
                return new ResponseEntity<>(authenticationService.login(user.getUsername(), user.getPassword(),true), HttpStatus.OK);
            }catch(Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user){

        //autorizacija

        UserDTO userDTO=userService.updateUser(user);
        if(userDTO==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return  new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id){

        boolean flag = userService.deleteUser(id);
        if (flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/activated/{id}")
    public ResponseEntity<Boolean> checkActivated(@PathVariable("id") long id){
        UserDTO user=userService.findUserById(id);
        if(user!=null){
//            System.out.println(user.getStatus());
//            System.out.println(AccountStatus.ACTIVATED);
            return  new ResponseEntity<>(user.getStatus().equals(AccountStatus.ACTIVATED),HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/blocked/{id}")
    public ResponseEntity<Boolean> checkBlocked(@PathVariable("id") long id) {
        UserDTO user = userService.findUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user.getStatus().equals(AccountStatus.BLOCKED), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) {
        UserDTO user = userService.findUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
