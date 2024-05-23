package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.enums.AccountStatus;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User,Long> {
    User findByUsernameAndStatus(String username, AccountStatus status);
    User findByUsername(String username);
    List<User> findAllByStatus(AccountStatus status);
}
