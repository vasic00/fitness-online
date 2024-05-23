package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.AdminDAO;
import unibl.etf.ip.fitnessonline.model.Admin;
import unibl.etf.ip.fitnessonline.services.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final AdminDAO adminDAO;

    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public Admin login(String username, String password){
        return adminDAO.findByUsernameAndPassword(username,password);
    }
}
