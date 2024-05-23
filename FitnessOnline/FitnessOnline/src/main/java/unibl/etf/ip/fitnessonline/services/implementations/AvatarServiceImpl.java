package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.AvatarDAO;
import unibl.etf.ip.fitnessonline.services.AvatarService;

import java.util.List;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    private final AvatarDAO avatarDAO;

    public AvatarServiceImpl(AvatarDAO avatarDAO){
        this.avatarDAO=avatarDAO;
    }
    public List<String> getAllAvatars(){
        return avatarDAO.getAvatars();
    }
    public byte[] getAvatar(String avatar){
        return avatarDAO.getAvatarByName(avatar);
    }
}
