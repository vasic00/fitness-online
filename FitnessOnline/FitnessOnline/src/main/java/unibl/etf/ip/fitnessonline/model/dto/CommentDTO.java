package unibl.etf.ip.fitnessonline.model.dto;


import lombok.Data;

@Data
public class CommentDTO {
    private long id;
    private String content;
    private String creatorInfo;
    private long creatorId;
    private String time;
    private long programId;
    private String creatorAvatar;

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creatorInfo='" + creatorInfo + '\'' +
                ", time='" + time + '\'' +
                ", programId=" + programId +
                ", creatorAvatar='" + creatorAvatar + '\'' +
                '}';
    }
}
