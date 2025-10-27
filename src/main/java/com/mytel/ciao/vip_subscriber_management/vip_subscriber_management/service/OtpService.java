package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;


import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Otp;

public interface OtpService {
    Otp generateOtp(String isdn);

    Otp validateOtp(String otp, String isdn);

}
