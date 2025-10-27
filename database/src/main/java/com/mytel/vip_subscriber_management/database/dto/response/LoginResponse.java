package com.mytel.vip_subscriber_management.database.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginResponse {
    private String otpId;
    private LocalDateTime expireAt;
}
