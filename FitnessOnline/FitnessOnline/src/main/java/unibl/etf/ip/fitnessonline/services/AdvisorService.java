package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.Advisor;

import java.util.List;

public interface AdvisorService {
    Advisor login(String username, String password);
    Advisor openAdvisorAccount(Advisor advisor);
    List<Advisor> getAll();
}
