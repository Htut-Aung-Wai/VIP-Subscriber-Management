package com.mytel.vip_subscriber_management.service.mail;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
