package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.MailStructure;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    public String fromMail;

    public void sendMail(String recipient, MailStructure mailStructure) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(recipient);
        mailSender.send(simpleMailMessage);
    }

    public void sendMailWidthHTML(String[] recipient, MailStructure mailStructure) throws MessagingException {
        log.info("Mailing message to {} recipients", recipient.length);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(fromMail);
        helper.setTo(recipient);
        helper.setText(mailStructure.getMessage(), true);
        helper.setSubject(mailStructure.getSubject());
        mailSender.send(mimeMessage);
        log.info("Mailing successfully completed");
    }
}
