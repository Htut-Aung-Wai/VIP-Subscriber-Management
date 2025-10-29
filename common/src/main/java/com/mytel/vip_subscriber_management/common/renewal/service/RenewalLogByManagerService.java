package com.mytel.vip_subscriber_management.common.renewal.service;

import com.example.vip_management.renewal.entity.RenewByManager;
import com.example.vip_management.renewal.entity.RenewByManagerLog;

import java.util.List;

public interface RenewalLogByManagerService {

    void logCreated(RenewByManager renewByManager);

    List<RenewByManagerLog> getAllLogs();
}
