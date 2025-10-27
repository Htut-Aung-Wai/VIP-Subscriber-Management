package com.mytel.vip_subscriber_management.database.dto;



import com.mytel.vip_subscriber_management.database.dto.request.LoginConfirmRequest;
import com.mytel.vip_subscriber_management.database.dto.request.LoginRequest;
import com.mytel.vip_subscriber_management.database.dto.response.LoginConfirmResponse;
import com.mytel.vip_subscriber_management.database.dto.response.LoginResponse;


public interface UserService {
    LoginResponse validateIsdnAndSendOTP(LoginRequest request);
    LoginResponse resendOTP(LoginRequest request);
    LoginConfirmResponse validateOTP(LoginConfirmRequest verify);
}
