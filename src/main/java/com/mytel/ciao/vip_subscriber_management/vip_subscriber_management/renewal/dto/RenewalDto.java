package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.dto;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.entity.RenewByManager;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RenewalDto {

    private List<RenewItem> decisions;

    @Data
    public static class RenewItem {
        private String subscriberId;
        private RenewByManager.Decision decision;
    }

}
