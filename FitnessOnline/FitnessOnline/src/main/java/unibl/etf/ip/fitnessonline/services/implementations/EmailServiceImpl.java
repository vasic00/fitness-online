package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unibl.etf.ip.fitnessonline.dao.ProgramDAO;
import unibl.etf.ip.fitnessonline.dao.UserDAO;
import unibl.etf.ip.fitnessonline.model.Category;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.EmailContent;
import unibl.etf.ip.fitnessonline.model.enums.AccountStatus;
import unibl.etf.ip.fitnessonline.services.EmailService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final LoggerBean loggerBean;
    private final UserDAO userDAO;
    private final ProgramDAO programDAO;

    @Value("${spring.mail.username}") private String sender;

    public EmailServiceImpl(JavaMailSender javaMailSender, LoggerBean loggerBean, UserDAO userDAO, ProgramDAO programDAO) {
        this.javaMailSender = javaMailSender;
        this.loggerBean = loggerBean;
        this.userDAO = userDAO;
        this.programDAO = programDAO;
    }

    @Override
    @Async
    public void sendMailFromAdvisor(String content, long user, ByteArrayResource byteArrayResource, String fileName){
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            User temp=userDAO.findById(user).get();
            helper.setTo(temp.getEmail());
            helper.setSubject("FitnessOnline Advisor");
            helper.setText(content);


            if (byteArrayResource.contentLength() > 0) {
                System.out.println("ATTACHING FILE");
                helper.addAttachment(fileName, byteArrayResource);
            }

            javaMailSender.send(message);
//            return true;
        }catch(Exception e){
            loggerBean.logError(e);
        }
//        return false;
    }


    @Override
    @Async
    public void sendEmail(EmailContent emailContent){
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailContent.getRecipient());
            mailMessage.setText(emailContent.getContent());
            mailMessage.setSubject(emailContent.getSubject());

            // Sending the mail
            System.out.println("mejl poslat!!!");
            javaMailSender.send(mailMessage);
//            return true;
        }
        catch (Exception e) {
            loggerBean.logError(e);
//            return false;
        }
    }

    // 8 AM is 0 0 8 * * *
    @Scheduled(cron = "0 42 13 * * *")
    @Transactional
    public void sendDailyNotifications() {
        String subject = "New programs for your chosen categories!";
        List<Program> programsToCheck = new ArrayList<>();
        List<User> users = userDAO.findAllByStatus(AccountStatus.ACTIVATED);
        for (User user : users) {
            StringBuilder mailContent = new StringBuilder();
            List<Category> categories = user.getSubscribedCategories();
            for (Category category : categories) {
                List<Program> programs = programDAO.findByCategoryAndUsersNotified(category, false);
                if (!programs.isEmpty()) {
                    mailContent.append("Category ").append(category.getName()).append(":").append(System.lineSeparator()).append(System.lineSeparator());
                    for (Program program : programs) {
                        if (!programsToCheck.contains(program))
                            programsToCheck.add(program);
                        if (!program.getCreator().equals(user) && !user.getParticipatedPrograms().contains(program)) {
                            mailContent.append("Program name: ").append(program.getName()).append(System.lineSeparator())
                                    .append("Description: ").append(program.getDescription()).append(System.lineSeparator())
                                    .append("Price: ").append(program.getPrice()).append(System.lineSeparator());
                            mailContent.append(System.lineSeparator());
                        }
                    }
                }
            }
            if (!mailContent.isEmpty()) {
                EmailContent emailContent = new EmailContent();
                emailContent.setSubject(subject);
                emailContent.setContent(mailContent.toString());
                emailContent.setRecipient(user.getEmail());
                sendEmail(emailContent);
            }
        }
        for (Program program : programsToCheck) {
            program.setUsersNotified(true);
            programDAO.save(program);
        }
    }
}
