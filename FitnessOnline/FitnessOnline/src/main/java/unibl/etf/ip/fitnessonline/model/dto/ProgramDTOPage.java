package unibl.etf.ip.fitnessonline.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProgramDTOPage {

    List<ProgramDTO> programs;
    private int index;
    private long totalElements;
    private int totalPages;

    @Override
    public String toString() {
        return "ProgramDTOPage{" +
                "programs=" + programs +
                ", index=" + index +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }
}
