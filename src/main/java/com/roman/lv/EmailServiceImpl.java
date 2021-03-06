package com.roman.lv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value( "${mail.subject}" )
    private String subject;
    @Value( "${mail.text}" )
    private String text;
    @Value( "${mail.to}" )
    private String to;

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    public void sendSimpleMessage(String url, String [] emailAddresses) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        System.out.println("inside sender " + emailAddresses);
        message.setTo(to);
        message.setBcc(emailAddresses);
        message.setSubject(subject);
        message.setText(text + " " + url);
        emailSender.send(message);
        log.info("Email is sent");
    }
}