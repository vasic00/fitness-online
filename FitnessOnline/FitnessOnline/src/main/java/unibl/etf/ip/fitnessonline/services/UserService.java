package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findUserByUsername(String username);
    UserDTO findUserById(long id);
    UserDTO addUser(UserDTO user);
    UserDTO updateUser(UserDTO user);
    UserDTO register(UserDTO user);
    boolean deleteUser(long user);
    public UserDTO login(String username,String password);
    List<UserDTO> getAllUsers();
   // void deleteUserById(long id);
    UserDTO checkCredentials(String username,String password);
    void activate(UserDTO user);
    boolean validateCredentials(String username,String password);
}
