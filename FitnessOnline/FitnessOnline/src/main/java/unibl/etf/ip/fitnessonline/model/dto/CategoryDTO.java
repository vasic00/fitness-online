package unibl.etf.ip.fitnessonline.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private long id;
    private String name;
    private List<SpecificAttributeDTO> specificAttributes;

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specificAttributes=" + specificAttributes +
                '}';
    }
}
