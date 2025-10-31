package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.entity.Otp;

public interface OtpService {

    Otp generateOtp(String isdn);

    Otp validateOtp(String otp, String isdn);

}
