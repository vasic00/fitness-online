package unibl.etf.ip.fitnessonline.model.dto;

import lombok.Data;

@Data
public class ProgramImageDTO {
    private long id;
    private String img;
    private long programId;

    @Override
    public String toString() {
        return "ProgramImageDTO{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", programId=" + programId +
                '}';
    }
}
