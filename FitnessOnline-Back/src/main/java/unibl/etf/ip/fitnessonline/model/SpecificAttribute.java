package unibl.etf.ip.fitnessonline.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class SpecificAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name="category",referencedColumnName = "id", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "specificAttribute", fetch = FetchType.LAZY)
    private List<Attribute> attributes;
}
