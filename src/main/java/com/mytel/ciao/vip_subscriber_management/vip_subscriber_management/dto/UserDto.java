package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String phoneNumber;
    private Boolean isActive;
}
