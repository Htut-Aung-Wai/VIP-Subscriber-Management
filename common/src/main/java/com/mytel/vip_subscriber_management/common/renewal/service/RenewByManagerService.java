package com.mytel.vip_subscriber_management.common.renewal.service;

import com.example.vip_management.renewal.dto.RenewalDto;
import com.example.vip_management.renewal.entity.RenewByManager;

import java.util.List;

public interface RenewByManagerService {

    List<RenewByManager> renewByManager(RenewByManager renewByManager, String branch);

    List<RenewByManager> partialRenewByManager(RenewalDto dto);

}
