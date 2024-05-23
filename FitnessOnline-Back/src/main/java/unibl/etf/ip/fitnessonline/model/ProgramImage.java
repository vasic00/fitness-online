package unibl.etf.ip.fitnessonline.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProgramImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String img;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program", referencedColumnName = "id", nullable = false)
    private Program program;

}
