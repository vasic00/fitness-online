package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Purchase;
import unibl.etf.ip.fitnessonline.model.User;

import java.util.List;

@Repository
public interface PurchaseDAO extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(User user);
}
