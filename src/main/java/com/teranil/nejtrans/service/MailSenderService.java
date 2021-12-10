package com.teranil.nejtrans.service;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void SendEmail(String To, String Subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(To);
        message.setText(body);
        message.setSubject(Subject);
        javaMailSender.send(message);

    }


}
