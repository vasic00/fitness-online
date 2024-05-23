package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.*;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.UserDTO;
import unibl.etf.ip.fitnessonline.model.enums.AccountStatus;
import unibl.etf.ip.fitnessonline.services.*;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final ProgramDAO programDAO;
    private final MessageDAO messageDAO;
    private final CommentDAO commentDAO;
    private final PurchaseDAO purchaseDAO;
    private final ExerciseDAO exerciseDAO;
    private final AttributeDAO attributeDAO;
    private final ProgramImageDAO programImageDAO;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final LoggerBean loggerBean;


    public UserServiceImpl(UserDAO userDAO, ProgramDAO programDAO, ModelMapper modelMapper, LoggerBean loggerBean, CommentService commentService, MessageService messageService, MessageDAO messageDAO, CommentDAO commentDAO, PurchaseDAO purchaseDAO, ExerciseDAO exerciseDAO, AttributeDAO attributeDAO, ProgramImageDAO programImageDAO) {
        this.userDAO = userDAO;
        this.programDAO = programDAO;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
        this.messageDAO = messageDAO;
        this.commentDAO = commentDAO;
        this.purchaseDAO = purchaseDAO;
        this.exerciseDAO = exerciseDAO;
        this.attributeDAO = attributeDAO;
        this.programImageDAO = programImageDAO;
    }

    public UserDTO findUserByUsername(String username) {
        User user = userDAO.findByUsername(username);
        if (user != null)
            return modelMapper.map(user, UserDTO.class);
        else return null;
    }

    public UserDTO findUserById(long id) {
        try{
        User user = userDAO.findById(id).get();
        if (user != null)
            return modelMapper.map(user, UserDTO.class);
        else return null;
        }catch(Exception e){
            loggerBean.logError(e);
            return null;
        }
    }

    public UserDTO addUser(UserDTO user) {
        User userEntity = modelMapper.map(user, User.class);
        userEntity.setPassword(encoder.encode(user.getPassword()));
        User temp = userDAO.save(userEntity);
        System.out.println(temp.getId());
        UserDTO userDTO = modelMapper.map(temp, UserDTO.class);
        userDTO.setId(temp.getId());
        System.out.println(userDTO.getId());
        return userDTO;
    }

    public UserDTO updateUser(UserDTO user) {
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        User userEntity = userDAO.findById(user.getId()).get();

        if (userEntity == null || !validateCredentials(user.getUsername(),user.getPassword()))
            return null;
        if (user.getUsername() != null && user.getUsername().length() >= 5) {
            User userEntityOther = userDAO.findByUsername(user.getUsername());
            if (userEntityOther != null && userEntityOther.getId() != userEntity.getId())
                return null;
            else userEntity.setUsername(user.getUsername());
        }
        userEntity.setCity(user.getCity());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        if (!user.getPassword().equals(userEntity.getPassword()))
            userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setAvatar(user.getAvatar());
        userEntity.setStatus(user.getStatus());
        User temp = userDAO.save(userEntity);
        return modelMapper.map(temp, UserDTO.class);
    }

    public void activate(UserDTO user) {
        User userEntity = userDAO.findById(user.getId()).get();
        if (userEntity != null) {
            userEntity.setStatus(user.getStatus());
            User temp = userDAO.save(userEntity);
        }
    }

    public UserDTO register(UserDTO user) {
        if (userDAO.findByUsername(user.getUsername()) != null)
            return null;
        else if(!validateCredentials(user.getUsername(),user.getPassword()))
        return null;
        else{
            user.setStatus(AccountStatus.NOT_ACTIVATED);
            System.out.println("Uspjesno u register u userserviceimpl");
            return addUser(user);
        }
    }

    @Override
    public boolean deleteUser(long user) {
       try {
           User userEntity = userDAO.findById(user).get();
           for (Program p : userEntity.getPrograms()) {
               commentDAO.deleteAll(p.getComments());
               attributeDAO.deleteAll(p.getAttributes());
               programImageDAO.deleteAll(p.getImages());
           }
           programDAO.deleteAll(userEntity.getPrograms());
           messageDAO.deleteAll(userEntity.getSentMessages());
           messageDAO.deleteAll(userEntity.getReceivedMessages());
           commentDAO.deleteAll(userEntity.getComments());
           purchaseDAO.deleteAll(userEntity.getPurchases());
           exerciseDAO.deleteAll(userEntity.getExercises());
           userDAO.delete(userEntity);
           return true;
       } catch (Exception e) {
           loggerBean.logError(e);
           return false;
       }
    }

    public boolean validateCredentials(String username,String password){
        System.out.println(username+" "+password);
        if(username.length()>=5 && password.length()>=8){
          //  Matcher m = p.matcher(password);
          //  return m.matches();
            return true;
        }
        else return false;
    }

    public UserDTO login(String username, String password) {
        UserDTO user = findUserByUsername(username);
        if (user == null)
            return null;
        else if (encoder.matches(password, user.getPassword()))
            return user;
        else return null;
    }

    public List<UserDTO> getAllUsers() {
        List<User> list = userDAO.findAll();
        List<UserDTO> result = new ArrayList<UserDTO>();
        list.stream().forEach((u) -> {
            result.add(modelMapper.map(u, UserDTO.class));
        });
        return result;
    }

    public UserDTO checkCredentials(String username, String password) {
        UserDTO user = findUserByUsername(username);
        if (user != null && encoder.matches(password, user.getPassword()))
            return user;
        else
            return null;
    }
}
