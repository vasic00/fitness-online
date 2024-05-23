package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.ExerciseDAO;
import unibl.etf.ip.fitnessonline.dao.UserDAO;
import unibl.etf.ip.fitnessonline.model.Exercise;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.ExerciseDTO;
import unibl.etf.ip.fitnessonline.model.dto.UserDTO;
import unibl.etf.ip.fitnessonline.services.ExerciseService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService{

    private final ExerciseDAO exerciseDAO;
    private final UserDAO userDAO;
    private final LoggerBean loggerBean;

    public ExerciseServiceImpl(ExerciseDAO exerciseDAO, UserDAO userDAO, LoggerBean loggerBean) {
        this.exerciseDAO = exerciseDAO;
        this.userDAO = userDAO;
        this.loggerBean = loggerBean;
    }

    @Override
    public ExerciseDTO add(ExerciseDTO exerciseDTO, long userId) {
        try {
            User user = userDAO.findById(userId).get();
            Exercise exercise = new Exercise();
            exercise.setName(exerciseDTO.getName());
            exercise.setDuration(exerciseDTO.getDuration());
            exercise.setResult(exerciseDTO.getResult());
            exercise.setIntensity(exerciseDTO.getIntensity());
            exercise.setUser(user);
            exercise.setDate(LocalDate.now());
            Exercise result = exerciseDAO.save(exercise);
            ExerciseDTO resultDTO = new ExerciseDTO();
            resultDTO.setId(result.getId());
            resultDTO.setDate(result.getDate());
            resultDTO.setName(result.getName());
            resultDTO.setDuration((result.getDuration()));
            resultDTO.setIntensity(result.getIntensity());
            resultDTO.setResult(result.getResult());
            return resultDTO;
        } catch (Exception ex) {
            loggerBean.logError(ex);
            return null;
        }
    }

    @Override
    public List<ExerciseDTO> getAllByUser(long userId) {
        try{
            User user = userDAO.findById(userId).get();
            List<Exercise> exercises = exerciseDAO.findAllByUser(user);
            ArrayList<ExerciseDTO> exerciseDTOS = new ArrayList<>();
            exercises.forEach(ex -> {
                ExerciseDTO exerciseDTO = new ExerciseDTO();
                exerciseDTO.setDate(ex.getDate());
                exerciseDTO.setResult(ex.getResult());
                exerciseDTO.setName(ex.getName());
                exerciseDTO.setIntensity(ex.getIntensity());
                exerciseDTO.setId(ex.getId());
                exerciseDTO.setDuration(ex.getDuration());
                exerciseDTOS.add(exerciseDTO);
            });
            return exerciseDTOS;
        } catch (Exception ex) {
            loggerBean.logError(ex);
            return new ArrayList<>();
        }
    }
}
