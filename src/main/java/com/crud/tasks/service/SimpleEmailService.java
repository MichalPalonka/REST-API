package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private MailCreatorService mailCreatorService;

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has benn sent.");

        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            if (mail.getSubject().equals("Tasks: Once a day email")) {
                messageHelper.setText(mailCreatorService.buildTaskCountEmial(mail.getMessage()), true);
            } else {
                messageHelper.setText(mailCreatorService.buildTrelloCardEmil(mail.getMessage()), true);
            }
        };
    }
}
