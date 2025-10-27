package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginResponse {
    private String otpId;
    private LocalDateTime expireAt;
}
