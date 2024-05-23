package unibl.etf.ip.fitnessonline.services.implementations;

import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.StatisticsDAO;
import unibl.etf.ip.fitnessonline.services.StatisticsService;

import java.util.List;
@Service
public class StatisticsServiceImpl  implements StatisticsService {
private final StatisticsDAO statisticsDAO;

    public StatisticsServiceImpl(StatisticsDAO statisticsDAO) {
        this.statisticsDAO = statisticsDAO;
    }

    public List<String> getStatistics(){
        return statisticsDAO.readStatistics();
    }
}
