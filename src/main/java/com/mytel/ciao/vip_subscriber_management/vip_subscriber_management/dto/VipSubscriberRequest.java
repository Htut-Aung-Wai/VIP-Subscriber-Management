package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class VipSubscriberRequest {


    @NotBlank(message = "VIP Package ID is required")
    private String vipPackageId;

    private String subscriberNo;

    @NotBlank(message = "Branch name is required")
    private String branchName;

    @NotBlank(message = "Proposal Document No is required")
    private String proposalDocumentNo;



}
