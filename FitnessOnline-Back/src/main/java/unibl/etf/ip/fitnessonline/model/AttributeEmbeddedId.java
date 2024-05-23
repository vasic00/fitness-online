package unibl.etf.ip.fitnessonline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class AttributeEmbeddedId implements Serializable {

    @Column(name = "program_id")
    private long programId;
    @Column(name = "specific_attribute_id")
    private long specificAttributeId;

    public AttributeEmbeddedId(long programId, long specificAttributeId) {
        this.programId = programId;
        this.specificAttributeId = specificAttributeId;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public long getSpecificAttributeId() {
        return specificAttributeId;
    }

    public void setSpecificAttributeId(long specificAttributeId) {
        this.specificAttributeId = specificAttributeId;
    }
}
