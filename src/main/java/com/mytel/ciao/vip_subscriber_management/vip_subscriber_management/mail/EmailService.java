package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.mail;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
