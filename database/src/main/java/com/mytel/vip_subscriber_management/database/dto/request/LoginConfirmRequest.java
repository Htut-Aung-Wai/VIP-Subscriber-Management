package com.mytel.vip_subscriber_management.database.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginConfirmRequest {
    private String isdn;
    private String otp;

}
