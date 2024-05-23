package unibl.etf.ip.fitnessonline.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private boolean unread;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name="sender", referencedColumnName = "id")
    private User sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name="receiver", referencedColumnName = "id", nullable = true)
    private User receiver;
    private LocalDateTime timeOfSending;
}
