package unibl.etf.ip.fitnessonline.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String paymentType;
    private String time;
    private String programCategory;
    private String programName;
    private double programPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user", referencedColumnName = "id")
    private User user;

}
