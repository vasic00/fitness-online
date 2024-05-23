package unibl.etf.ip.fitnessonline.services;

import org.springframework.data.domain.Pageable;
import unibl.etf.ip.fitnessonline.model.Category;
import unibl.etf.ip.fitnessonline.model.dto.JwtUser;
import unibl.etf.ip.fitnessonline.model.dto.ProgramDTO;
import unibl.etf.ip.fitnessonline.model.dto.ProgramDTOPage;

import java.util.List;

public interface ProgramService {
    ProgramDTOPage getAllPrograms(Pageable pageable);
    ProgramDTOPage getProgramsByCreator(long id,Pageable pageable);
    boolean sellProgram(long id);
    ProgramDTO add(ProgramDTO program, String rand);
    boolean delete(long id,long user);
    void deleteAdmin(long id);
    ProgramDTO getById(long id);
    boolean checkIfParticipated(long id, JwtUser jwtUser);
    List<Long> getParticipations(JwtUser jwtUser);
    ProgramDTOPage getFiltered(double p1, double p2, String valid, String categoryName, String name, int difficultyLevel, JwtUser jwtUser, Pageable pageable);
    ProgramDTOPage getFilteredByCreator(double p1, double p2, String valid, String categoryName, String name, int difficultyLevel, JwtUser jwtUser, Pageable pageable);
}
