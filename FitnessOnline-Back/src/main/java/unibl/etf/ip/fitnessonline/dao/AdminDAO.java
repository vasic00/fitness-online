package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Admin;

@Repository
public interface AdminDAO extends JpaRepository<Admin, String>{
    Admin findByUsernameAndPassword(String username, String password);
}
