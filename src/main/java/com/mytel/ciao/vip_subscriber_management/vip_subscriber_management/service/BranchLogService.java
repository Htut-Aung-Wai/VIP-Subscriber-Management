package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitLog;

import java.util.List;

public interface UnitLogService {

    void logCreated(Branch branch);

    UnitLog logUpdated(Branch oldBranch, Branch newBranch);

    void logDeleted(Branch deletedBranch);

    List<UnitLog> getAllLogs();

    List<UnitLog> getLogsByUnitName(String unitName);

    List<UnitLog> getLogsByUnitNameAndAction(String unitName, String actionType);
}
