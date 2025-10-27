package com.mytel.vip_subscriber_management.database.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String phoneNumber;
    private Boolean isActive;
}
