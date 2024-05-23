package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Category;

import java.util.List;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Long>{
    Category findByName(String name);
}
