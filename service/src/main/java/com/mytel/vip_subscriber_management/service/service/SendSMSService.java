package com.mytel.vip_subscriber_management.service.service;

public interface SendSMSService {
    void sendSMS(String source, String address, String content);
}
