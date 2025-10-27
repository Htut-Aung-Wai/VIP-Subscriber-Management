package com.mytel.vip_subscriber_management.database.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VipSubscriberExcelImportCreateErrorDto {

    private String phoneNumber;
    private String error;
}
