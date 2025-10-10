package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.mail.impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            message.setFrom("johndane0239@gmail.com");
            System.out.println("Email sent to " + to + "---------------");
        } catch (Exception e) {
            System.out.println("Error sending email! " + e.getMessage());
        }
    }
}
