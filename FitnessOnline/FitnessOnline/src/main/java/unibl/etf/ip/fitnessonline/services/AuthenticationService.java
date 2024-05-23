package unibl.etf.ip.fitnessonline.services;


import unibl.etf.ip.fitnessonline.model.dto.LoginResponse;
import unibl.etf.ip.fitnessonline.model.exceptions.BlockedAccountException;

public interface AuthenticationService {
    LoginResponse login(String username, String password,boolean sendEmail) throws BlockedAccountException;
    boolean activateAccount(String activationToken);
}
