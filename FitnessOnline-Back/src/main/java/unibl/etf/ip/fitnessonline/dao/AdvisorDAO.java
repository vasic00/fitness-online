package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Advisor;

@Repository
public interface AdvisorDAO extends JpaRepository<Advisor, String> {
    Advisor findByUsernameAndPassword(String username, String password);
    Advisor findByUsername(String username);

}
