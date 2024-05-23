package unibl.etf.ip.fitnessonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FitnessOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessOnlineApplication.class, args);
    }

}
