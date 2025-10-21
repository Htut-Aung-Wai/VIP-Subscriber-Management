package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Unit;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitLog;

import java.util.List;

public interface UnitLogService {

    void logCreated(Unit unit);

    UnitLog logUpdated(Unit oldUnit, Unit newUnit);

    void logDeleted(Unit deletedUnit);

    List<UnitLog> getAllLogs();

    List<UnitLog> getLogsByUnitName(String unitName);

    List<UnitLog> getLogsByUnitNameAndAction(String unitName, UnitLog.ActionType actionType);
}
