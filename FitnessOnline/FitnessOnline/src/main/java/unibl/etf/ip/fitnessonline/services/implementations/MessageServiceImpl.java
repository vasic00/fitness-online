package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.MessageDAO;
import unibl.etf.ip.fitnessonline.dao.UserDAO;
import unibl.etf.ip.fitnessonline.model.Message;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.MessageDTO;
import unibl.etf.ip.fitnessonline.services.MessageService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    private final MessageDAO messageDAO;
    private final UserDAO userDAO;
    private final LoggerBean loggerBean;

    public MessageServiceImpl(MessageDAO messageDAO, UserDAO userDAO, LoggerBean loggerBean) {
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
        this.loggerBean = loggerBean;
    }

    private MessageDTO map(Message message) {
        MessageDTO temp=new MessageDTO();
        temp.setId(message.getId());
        temp.setUnread(message.isUnread());
        temp.setContent(message.getContent());
        temp.setSenderId(message.getSender().getId());
        temp.setReceiverId(message.getReceiver().getId());
        temp.setSender(message.getSender().getFirstname()+" "+message.getSender().getLastname());
        temp.setTimeOfSending(message.getTimeOfSending());
        return temp;
    }

    public List<MessageDTO> getAllForAdvisors(){
        return messageDAO.findAllByUnreadAndReceiverIsNull(true).stream().map((m) -> {
            MessageDTO temp=new MessageDTO();
            temp.setId(m.getId());
            temp.setUnread(m.isUnread());
            temp.setContent(m.getContent());
            temp.setSenderId(m.getSender().getId());
            temp.setReceiverId(-1);
            temp.setSender(m.getSender().getFirstname()+" "+m.getSender().getLastname());
            return temp;
        }).collect(Collectors.toList());
    }
    public List<MessageDTO> getAllFilteredForAdvisors(String unread, String key){
        if ("-".equals(key))
            key = "";
        boolean unreadBool = "true".equals(unread);
        return messageDAO.findAllByUnreadAndContentContainingAndReceiverIsNull(unreadBool,key).stream().map((m) -> {
            MessageDTO temp=new MessageDTO();
            temp.setId(m.getId());
            temp.setUnread(m.isUnread());
            temp.setContent(m.getContent());
            temp.setSenderId(m.getSender().getId());
            temp.setReceiverId(-1);
            temp.setSender(m.getSender().getFirstname()+" "+m.getSender().getLastname());
            temp.setTimeOfSending(m.getTimeOfSending());
            return temp;
        }).collect(Collectors.toList());
    }

//    @Override
//    public List<MessageDTO> getAllReceivedByUser(long userId, String content) {
//        try{
//            User receiver = userDAO.findById(userId).get();
//            return messageDAO.findAllByReceiver(receiver).stream().map(this::map).collect(Collectors.toList());
//        }
//        catch (Exception e) {
//            loggerBean.logError(e);
//            return new ArrayList<MessageDTO>();
//        }
//    }

    @Override
    public List<MessageDTO> getReceivedByUser(long userId, String unread, String key) {
        try{
            boolean unreadBool = unread.equals("true");
            User receiver = userDAO.findById(userId).get();
            if (key.equals("-"))
                key = "";
            if (unread.equals("-"))
                return messageDAO.findAllByContentContainingAndReceiver(key, receiver).stream().map(this::map).collect(Collectors.toList());
            else return messageDAO.findAllByUnreadAndContentContainingAndReceiver(unreadBool,key, receiver).stream().map(this::map).collect(Collectors.toList());
        }
        catch (Exception e) {
            loggerBean.logError(e);
            return new ArrayList<MessageDTO>();
        }
    }

    @Override
    public void readReceivedByUser(long userId, long id) {
        try{
          //  User receiver = userDAO.findById(userId).get();
            Message message = messageDAO.findById(id).get();
            if (message.getReceiver().getId() == userId) {
                message.setUnread(false);
                messageDAO.save(message);
            }
        }
        catch (Exception e) {
            loggerBean.logError(e);
        }
    }

    public boolean add(MessageDTO messageDTO){
        try {
            User sender = userDAO.findById(messageDTO.getSenderId()).get();
            User receiver = userDAO.findById(messageDTO.getReceiverId()).orElse(null);
            Message message=new Message();
            message.setContent(messageDTO.getContent());
            message.setUnread(true);
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setTimeOfSending(LocalDateTime.now());
            messageDAO.save(message);
            return true;
        }catch(Exception e){
            loggerBean.logError(e);
            return false;
        }
    }

    public boolean deleteReceivedByUser(long userId, long id) {
        try {
            User user = userDAO.findById(userId).get();
            messageDAO.deleteByIdAndReceiver(id, user);
            return true;
        }catch(Exception e) {
            loggerBean.logError(e);
            return false;
        }
    }
    public MessageDTO advisorRead(long id){
    try {
        Message message = messageDAO.findById(id).get();
        if (message.getReceiver() == null) {
            message.setUnread(false);
            message = messageDAO.save(message);
            MessageDTO result = new MessageDTO();
            result.setUnread(message.isUnread());
            result.setSenderId(message.getSender().getId());
            result.setReceiverId(-1);
            result.setId(message.getId());
            result.setContent(message.getContent());
            result.setTimeOfSending(message.getTimeOfSending());
            result.setSender(message.getSender().getFirstname() + " " + message.getSender().getLastname());
            return result;
        }
        return null;
    }catch(Exception e){
        loggerBean.logError(e);
        return null;
    }
    }

    public boolean advisorDelete(long id){
        try {
            Message message = messageDAO.findById(id).get();
            if (message.getReceiver() == null) {
                messageDAO.deleteById(id);
                return true;
            }
            else return false;
        }catch(Exception e) {
            loggerBean.logError(e);
            return false;
        }
    }
}
