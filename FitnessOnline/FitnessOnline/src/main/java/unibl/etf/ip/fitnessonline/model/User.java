package unibl.etf.ip.fitnessonline.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import unibl.etf.ip.fitnessonline.model.enums.AccountStatus;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String city;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String avatar;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Program> programs;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Message> sentMessages;
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Message> receivedMessages;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Purchase> purchases;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Exercise> exercises;
    @ManyToMany
    @JoinTable(name = "participation", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "program_id"))
    private List<Program> participatedPrograms;
    @ManyToMany
    @JoinTable(name = "subscription", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> subscribedCategories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
