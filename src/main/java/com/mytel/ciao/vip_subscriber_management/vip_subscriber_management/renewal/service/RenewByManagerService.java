package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.dto.RenewalDto;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.entity.RenewByManager;

import java.util.List;

public interface RenewByManagerService {

    public List<RenewByManager> renewByManager(RenewByManager renewByManager, String branch);

    List<RenewByManager> partialRenewByManager(RenewalDto dto);

}
