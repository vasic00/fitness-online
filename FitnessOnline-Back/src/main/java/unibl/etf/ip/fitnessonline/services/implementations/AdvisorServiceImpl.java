package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.AdvisorDAO;
import unibl.etf.ip.fitnessonline.model.Advisor;
import unibl.etf.ip.fitnessonline.services.AdvisorService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.util.List;

@Service
@Transactional
public class AdvisorServiceImpl implements AdvisorService {

    private final AdvisorDAO advisorDAO;
    private final LoggerBean loggerBean;

    public AdvisorServiceImpl(AdvisorDAO advisorDAO, LoggerBean loggerBean) {
        this.advisorDAO = advisorDAO;
        this.loggerBean = loggerBean;
    }

    @Override
    public Advisor login(String username, String password) {
        return advisorDAO.findByUsernameAndPassword(username,password);
    }

    @Override
    public Advisor openAdvisorAccount(Advisor advisor) {
        try {
            if (advisorDAO.findByUsername(advisor.getUsername()) != null)
                return null;
           return advisorDAO.save(advisor);
        } catch (Exception e) {
            this.loggerBean.logError(e);
            return null;
        }
    }

    @Override
    public List<Advisor> getAll() {
        return advisorDAO.findAll();
    }
}
