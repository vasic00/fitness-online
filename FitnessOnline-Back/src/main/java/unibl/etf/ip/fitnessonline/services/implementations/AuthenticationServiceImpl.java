package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.model.dto.EmailContent;
import unibl.etf.ip.fitnessonline.model.dto.JwtUser;
import unibl.etf.ip.fitnessonline.model.dto.LoginResponse;
import unibl.etf.ip.fitnessonline.model.dto.UserDTO;
import unibl.etf.ip.fitnessonline.model.enums.AccountStatus;
import unibl.etf.ip.fitnessonline.model.exceptions.BlockedAccountException;
import unibl.etf.ip.fitnessonline.security.JwtUtil;
import unibl.etf.ip.fitnessonline.services.AuthenticationService;
import unibl.etf.ip.fitnessonline.services.EmailService;
import unibl.etf.ip.fitnessonline.services.UserService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${server.port}")
    private String serverPort;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    private String activatePath = "http://localhost:serverPortcontextPath" + "/users/activate?token=";
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final HashMap<String,String> activationTokens;
    private final LoggerBean loggerBean;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService, EmailService emailService, ModelMapper modelMapper, JwtUtil jwtUtil, LoggerBean loggerBean) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.loggerBean = loggerBean;
        activationTokens=new HashMap<>();
    }

    @Override
    public LoginResponse login(String username, String password,boolean sendEmail) throws BlockedAccountException {
        System.out.println("Auth service hit! "+username+" "+password);
        LoginResponse loginResponse = null;
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    username, password
                            )
                    );
            //authenticated user
            JwtUser user = (JwtUser) authenticate.getPrincipal();
            UserDTO userDTO = userService.findUserById(user.getId());
            loginResponse = modelMapper.map(userDTO, LoginResponse.class);
            if (userDTO.getStatus().equals(AccountStatus.ACTIVATED)) {
                // generating jwt
                String token = jwtUtil.generateToken(user);
                System.out.println(token);
                loginResponse.setToken(token);
            }
            else if(userDTO.getStatus().equals(AccountStatus.BLOCKED)){
                throw new BlockedAccountException();
            }
            else if(userDTO.getStatus().equals(AccountStatus.NOT_ACTIVATED) && sendEmail){
                String activationToken = UUID.randomUUID().toString();
                System.out.println("put "+userDTO.getUsername()+" "+activationToken);
                activationTokens.put(activationToken,userDTO.getUsername());
                activatePath = activatePath.replace("serverPort", serverPort).replace("contextPath", contextPath);
                EmailContent email=new EmailContent(userDTO.getEmail(),user.getUsername(),activatePath + activationToken);
                emailService.sendEmail(email);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            loggerBean.logError(ex);
            throw ex;
        }
        return loginResponse;
    }

    @Override
    public boolean activateAccount(String token) {
        System.out.println("inside authentication service");
        String username = activationTokens.get(token);
        if (username != null) {
            System.out.println("username is not null");
                UserDTO user = userService.findUserByUsername(username);
                if (user != null) {
                    System.out.println("activating user");
                    activationTokens.remove(token);
                    user.setStatus(AccountStatus.ACTIVATED);
                    userService.activate(user);
                    return true;
                }
                System.out.println("User with that username not found!");
                return false;
        }
        System.out.println("Username not found!");
        return false;
    }
}
