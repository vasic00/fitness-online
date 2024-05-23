package unibl.etf.ip.fitnessonline.services;

import java.util.List;

public interface AvatarService {
    List<String> getAllAvatars();
    byte[] getAvatar(String avatar);
}
