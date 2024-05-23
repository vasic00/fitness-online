package unibl.etf.ip.fitnessonline.model.dto;

import lombok.Data;
import unibl.etf.ip.fitnessonline.model.User;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private long id;
    private String content;
    private boolean unread;
    private long senderId;
    private long receiverId;
    private String sender;
    private LocalDateTime timeOfSending;

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", senderId=" + senderId +
                ", unread=" + unread +
                '}';
    }
}
