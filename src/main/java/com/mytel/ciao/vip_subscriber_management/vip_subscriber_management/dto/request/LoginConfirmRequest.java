package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginConfirmRequest {
    private String isdn;
    private String otp;

}
