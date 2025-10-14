package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHead;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHeadLog;

import java.util.List;

public interface UnitHeadLogService {

    void logCreated(UnitHead unit);

    UnitHeadLog logUpdated(UnitHead oldUnit, UnitHead newUnit);

    void logDeleted(UnitHead deletedUnit);

    List<UnitHeadLog> getAllLogs();

    List<UnitHeadLog> getLogsByUnitName(String unitName);

    List<UnitHeadLog> getLogsByUnitNameAndAction(String unitName, UnitHeadLog.ActionType actionType);
}
