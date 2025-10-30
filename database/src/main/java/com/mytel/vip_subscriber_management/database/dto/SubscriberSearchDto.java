package com.mytel.vip_subscriber_management.database.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SubscriberSearchDto {

    private String keyword;
    private String subscriberNumber;
    private String documentNumber;
    private String unit;
    private LocalDate fromDate;
    private LocalDate toDate;
}
