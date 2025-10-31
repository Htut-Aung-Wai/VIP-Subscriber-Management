package com.mytel.vip_subscriber_management.database.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitHeadUpdateDto {

    private String unitHeadFullName;
    private String vmyCode;
    private String email;
    private String phoneNumber;
    private String remark;
}
