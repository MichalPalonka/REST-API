package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);


    @Autowired
    private JavaMailSender javaMailSender;

    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("Email has benn sent.");

        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e);
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        if (EmailValidator.getInstance().isValid(mail.getMailTo())) { mailMessage.setTo(mail.getMailTo()); }
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        Optional.ofNullable(mail.getToCc()).ifPresent(mailMessage::setCc);
        return mailMessage;

    }
}
