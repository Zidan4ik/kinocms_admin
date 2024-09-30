package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.MailStructure;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailService mailService;

    private MailStructure mailStructure;

    @BeforeEach
    void setUp() {
        mailStructure = new MailStructure("Test Subject", "Test Message");
        mailService.fromMail = "test@example.com";
    }

    @Test
    void testSendMail() {
        String recipient = "recipient@example.com";
        mailService.sendMail(recipient, mailStructure);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendMailWidthHTML() throws MessagingException {
        String[] recipients = {"recipient1@example.com", "recipient2@example.com"};
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        mailService.sendMailWidthHTML(recipients, mailStructure);
        verify(mailSender, times(1)).send(mimeMessage);
    }
}