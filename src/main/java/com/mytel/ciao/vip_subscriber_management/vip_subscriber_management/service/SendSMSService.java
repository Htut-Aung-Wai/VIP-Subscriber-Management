package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

public interface SendSMSService {
    void sendSMS(String source, String address, String content);
}
