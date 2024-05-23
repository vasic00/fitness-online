package unibl.etf.ip.fitnessonline.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;
    private int difficultyLevel;
    private LocalDateTime ending;
    private String instructorInfo;
    private String contact;
    private String location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", referencedColumnName = "id", nullable = false)
    private User creator;
    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    private List<Comment> comments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    private List<ProgramImage> images;
    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    private List<Attribute> attributes;
    @ManyToMany(mappedBy = "participatedPrograms")
    private List<User> participants;
    private boolean usersNotified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return id == program.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
