package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VipSubscriberExcelImportCreateErrorDto {

    private String phoneNumber;
    private String error;
}
