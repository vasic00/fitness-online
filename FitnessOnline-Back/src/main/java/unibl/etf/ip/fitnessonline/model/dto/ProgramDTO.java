package unibl.etf.ip.fitnessonline.model.dto;

import lombok.Data;
import unibl.etf.ip.fitnessonline.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProgramDTO {
    private long id;
    private String name;
    private String description;
    private String category;
    private String location;
    private double price;
    private int difficultyLevel;
    private LocalDateTime ending;
    private CreatorInfo creator;
    private String contact;
    private String instructorInfo;
    private List<ProgramImageDTO> images;
    private List<CommentDTO>  comments;
    private List<AttributeDTO> attributes;

    @Override
    public String toString() {
        return "ProgramDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", difficulty level=" + difficultyLevel +
                ", ending=" + ending +
                ", creator=" + creator +
                ", contact='" + contact + '\'' +
                ", instructor info=" + instructorInfo +
                ", images=" + images +
                ", comments=" + comments +
                '}';
    }
}
