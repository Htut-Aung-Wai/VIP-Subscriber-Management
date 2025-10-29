package com.mytel.vip_subscriber_management.service.service;



import com.mytel.vip_subscriber_management.database.dto.RenewalDto;
import com.mytel.vip_subscriber_management.database.entity.RenewByManager;

import java.util.List;

public interface RenewByManagerService {

    List<RenewByManager> renewByManager(RenewByManager renewByManager, String branch);

    List<RenewByManager> partialRenewByManager(RenewalDto dto);

}
