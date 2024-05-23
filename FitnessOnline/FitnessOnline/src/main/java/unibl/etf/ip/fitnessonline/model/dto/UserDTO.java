package unibl.etf.ip.fitnessonline.model.dto;

import lombok.Data;
import unibl.etf.ip.fitnessonline.model.enums.AccountStatus;

@Data
public class UserDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String city;
    private AccountStatus status;
    private String avatar;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                '}';
    }

}
