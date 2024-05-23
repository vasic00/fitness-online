package unibl.etf.ip.fitnessonline.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import unibl.etf.ip.fitnessonline.model.dto.EmailContent;

public interface EmailService {
    void sendEmail(EmailContent emailContent);
    void sendMailFromAdvisor(String content, long user, ByteArrayResource byteArrayResource, String fileName);
}
