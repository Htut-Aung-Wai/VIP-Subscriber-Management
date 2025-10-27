package com.mytel.vip_subscriber_management.service.service;



import java.util.List;

public interface UnitLogService {

    void logCreated(Unit unit);

    UnitLog logUpdated(Unit oldUnit, Unit newUnit);

    void logDeleted(Unit deletedUnit);

    List<UnitLog> getAllLogs();

    List<UnitLog> getLogsByUnitName(String unitName);

    List<UnitLog> getLogsByUnitNameAndAction(String unitName, String actionType);
}
