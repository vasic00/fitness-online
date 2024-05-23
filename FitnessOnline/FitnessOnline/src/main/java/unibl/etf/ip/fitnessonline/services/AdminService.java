package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.Admin;

public interface AdminService {
    Admin login(String username, String password);
}
