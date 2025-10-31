package com.mytel.vip_subscriber_management.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitHeadResponseDto {

    private String id;
    private String unitHeadFullName;
    private String vmyCode;
    private String email;
    private String phoneNumber;
    private String remark;
    private String unitCode;
    private String unitName;

}
