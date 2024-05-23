package unibl.etf.ip.fitnessonline.model.dto;

import lombok.Data;

@Data
public class EmailContent {
    public static final String ACTIVATION_EMAIL_SUBJECT="FitnessOnline Activate your account!";
    public static final String ACTIVATION_EMAIL_CONTENT="Hello USER. Please click the link below to activate your account:\n"+System.lineSeparator()+System.lineSeparator();
    private String recipient;
    private String subject;
    private String content;

public EmailContent(){}
    public EmailContent(String recipient, String user, String activationLink) {
        this.recipient = recipient;
        this.subject = ACTIVATION_EMAIL_SUBJECT;
        this.content = ACTIVATION_EMAIL_CONTENT.replace("USER",user) + activationLink;
    }

    @Override
    public String toString() {
        return "EmailContent{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
