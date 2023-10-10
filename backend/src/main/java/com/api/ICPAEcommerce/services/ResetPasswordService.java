package com.api.ICPAEcommerce.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmailResetPassword(String receiver, String subject, String name, String link) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject(subject);

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(RecoverPasswordEmailMessage.emailBody(name, link), true);
            helper.setTo(receiver);
            helper.setSubject(subject);

            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}

