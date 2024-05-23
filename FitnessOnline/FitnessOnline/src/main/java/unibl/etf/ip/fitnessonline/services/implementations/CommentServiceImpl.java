package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.CommentDAO;
import unibl.etf.ip.fitnessonline.dao.ProgramDAO;
import unibl.etf.ip.fitnessonline.dao.UserDAO;
import unibl.etf.ip.fitnessonline.model.Comment;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.CommentDTO;
import unibl.etf.ip.fitnessonline.model.dto.CreatorInfo;
import unibl.etf.ip.fitnessonline.services.CommentService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;
    private final UserDAO userDAO;
    private final ProgramDAO programDAO;
    private final ModelMapper modelMapper;
    private final LoggerBean loggerBean;

    public CommentServiceImpl(CommentDAO commentDAO, UserDAO userDAO, ProgramDAO programDAO, ModelMapper modelMapper, LoggerBean loggerBean) {
        this.commentDAO = commentDAO;
        this.userDAO = userDAO;
        this.programDAO = programDAO;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
    }

    public CommentDTO add(CommentDTO commentDTO){
        try {
//            User user = userDAO.findById(commentDTO.getCreatorInfo().getId()).get();
            User user = userDAO.findById(commentDTO.getCreatorId()).get();
            Program program=programDAO.findById(commentDTO.getProgramId()).get();
            DateFormat df= new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            commentDTO.setTime(df.format(new Date()));
            Comment comment=modelMapper.map(commentDTO, Comment.class);
            comment.setCreator(user);
            comment.setProgram(program);
            Comment result=commentDAO.save(comment);
            CommentDTO resultDTO=modelMapper.map(result,CommentDTO.class);
            resultDTO.setProgramId(program.getId());
//            CreatorInfo creatorInfo = new CreatorInfo();
//            creatorInfo.setId(user.getId());
//            creatorInfo.setInfo(user.getFirstname()+" "+user.getLastname());
            resultDTO.setCreatorId(user.getId());
            resultDTO.setCreatorInfo(user.getFirstname() + " " + user.getLastname());
            return resultDTO;
        }catch(Exception e){
    //        e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }
    public void delete(long id){
        commentDAO.deleteById(id);
    }
}
