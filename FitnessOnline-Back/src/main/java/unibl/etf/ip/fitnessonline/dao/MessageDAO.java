package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Message;
import unibl.etf.ip.fitnessonline.model.User;

import java.util.List;

@Repository
public interface MessageDAO extends JpaRepository<Message, Long> {
    List<Message> findAllByUnreadAndReceiverIsNull(boolean unread);
    List<Message> findAllByUnreadAndContentContainingAndReceiverIsNull(boolean unread, String content);
    List<Message> findAllByUnreadAndReceiver(boolean unread, User user);
    List<Message> findAllByReceiver(User user);
    List<Message> findAllByContentContainingAndReceiver(String content, User user);
    List<Message> findAllByUnreadAndContentContainingAndReceiver(boolean unread, String content, User user);
    void deleteByIdAndReceiver(long id, User receiver);

}
