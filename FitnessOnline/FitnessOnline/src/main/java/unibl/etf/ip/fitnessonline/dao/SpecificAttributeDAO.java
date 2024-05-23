package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Category;
import unibl.etf.ip.fitnessonline.model.SpecificAttribute;

import java.util.List;

@Repository
public interface SpecificAttributeDAO extends JpaRepository<SpecificAttribute, Long> {
    List<SpecificAttribute> findAllByCategory(Category category);
}
