package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import unibl.etf.ip.fitnessonline.model.Exercise;
import unibl.etf.ip.fitnessonline.model.User;

import java.util.List;

public interface ExerciseDAO extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByUser(User user);

}
