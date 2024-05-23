package unibl.etf.ip.fitnessonline.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.UserDAO;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.JwtUser;
import unibl.etf.ip.fitnessonline.services.JwtUserDetailsService;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private final UserDAO userDAO;
    private final ModelMapper modelMapper;

    public JwtUserDetailsServiceImpl(UserDAO userDAO, ModelMapper modelMapper) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
    }

    // retrieve active user info by specified username
    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userDAO.findByUsername(username);
        if(user!=null){
            System.out.println("Found");
            return modelMapper.map(user,JwtUser.class);
        }
        else{
            System.out.println("User not found!");
            throw new UsernameNotFoundException(username);
        }
    }
}
