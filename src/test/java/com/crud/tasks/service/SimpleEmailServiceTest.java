package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmailWithoutCc() {
        // Given
        Mail mail = new Mail("michalpalonka@gmail.com", "Test", "Test message");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(mail.getMessage());
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        Optional.ofNullable(mail.getToCc()).ifPresent(mailMessage::setCc);

        // When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
        Assert.assertNull(mail.getToCc());
    }
}