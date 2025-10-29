package com.mytel.vip_subscriber_management.common.renewal.dto;

import com.example.vip_management.renewal.entity.RenewByManager;
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
