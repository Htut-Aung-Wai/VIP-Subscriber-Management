package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.entity.RenewByManager;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.entity.RenewByManagerLog;

import java.util.List;

public interface RenewalLogByManagerService {

    void logCreated(RenewByManager renewByManager);

    List<RenewByManagerLog> getAllLogs();
}
