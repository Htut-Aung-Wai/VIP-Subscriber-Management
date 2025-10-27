package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;


import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.request.LoginConfirmRequest;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.request.LoginRequest;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.response.LoginConfirmResponse;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.response.LoginResponse;

public interface ConfirmPageUserService {
    LoginResponse validateIsdnAndSendOTP(LoginRequest request);
    LoginResponse resendOTP(LoginRequest request);
    LoginConfirmResponse validateOTP(LoginConfirmRequest verify);
}
