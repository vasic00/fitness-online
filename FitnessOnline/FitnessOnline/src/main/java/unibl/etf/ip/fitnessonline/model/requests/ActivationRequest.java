package unibl.etf.ip.fitnessonline.model.requests;

import lombok.Data;

@Data
public class ActivationRequest {
    private String username;
    private String password;
    private String activationToken;
}
