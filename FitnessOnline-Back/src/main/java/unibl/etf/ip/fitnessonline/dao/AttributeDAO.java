package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Attribute;
import unibl.etf.ip.fitnessonline.model.AttributeEmbeddedId;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.SpecificAttribute;

import java.util.List;

@Repository
public interface AttributeDAO extends JpaRepository<Attribute, AttributeEmbeddedId> {
    List<Attribute> findAllBySpecificAttribute(SpecificAttribute specificAttribute);
    List<Attribute> findAllByProgram(Program program);
    void deleteByProgramAndSpecificAttribute(Program program, SpecificAttribute sa);
}
