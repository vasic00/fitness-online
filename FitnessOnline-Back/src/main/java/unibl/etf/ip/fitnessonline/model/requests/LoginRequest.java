package unibl.etf.ip.fitnessonline.model.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
