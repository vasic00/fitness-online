package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getAllForAdvisors();
    List<MessageDTO> getAllFilteredForAdvisors(String unread, String key);
    List<MessageDTO> getReceivedByUser(long userId, String unread, String key);
    void readReceivedByUser(long userId, long id);
    boolean deleteReceivedByUser(long userId, long id);
    MessageDTO advisorRead(long id);
    boolean add(MessageDTO messageDTO);
    boolean advisorDelete(long id);
}
