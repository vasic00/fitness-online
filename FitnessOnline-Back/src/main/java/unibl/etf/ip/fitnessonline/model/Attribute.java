package unibl.etf.ip.fitnessonline.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Attribute {
    @EmbeddedId
    private AttributeEmbeddedId id;
    @ManyToOne
    @MapsId("programId")
    @JoinColumn(name = "program_id")
    Program program;
    @ManyToOne
    @MapsId("specificAttributeId")
    @JoinColumn(name = "specific_attribute_id")
    SpecificAttribute specificAttribute;
    private String value;
}
