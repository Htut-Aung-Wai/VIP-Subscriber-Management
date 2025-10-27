package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginConfirmResponse {
    private String accessToken;
    private LocalDateTime expiredAt;
    private Long duration;
//    private String refreshToken;
}
