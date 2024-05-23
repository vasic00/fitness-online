package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.dto.ExerciseDTO;
import unibl.etf.ip.fitnessonline.model.dto.UserDTO;

import java.util.List;

public interface ExerciseService {

    ExerciseDTO add(ExerciseDTO exerciseDTO, long userId);
    List<ExerciseDTO> getAllByUser(long userId);
}
