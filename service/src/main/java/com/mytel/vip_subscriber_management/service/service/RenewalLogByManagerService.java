package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.entity.RenewByManager;
import com.mytel.vip_subscriber_management.database.entity.RenewByManagerLog;

import java.util.List;

public interface RenewalLogByManagerService {

    void logCreated(RenewByManager renewByManager);

    List<RenewByManagerLog> getAllLogs();
}
