package unibl.etf.ip.fitnessonline.model.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unibl.etf.ip.fitnessonline.model.User;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ExerciseDTO {
    private long id;
    private String name;
    private int intensity;
    private double result;
    private int duration;
    private LocalDate date;
}
