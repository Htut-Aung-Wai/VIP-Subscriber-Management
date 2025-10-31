package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.entity.UnitHead;
import com.mytel.vip_subscriber_management.database.entity.UnitHeadLog;

import java.util.List;

public interface UnitHeadLogService {

    void logCreated(UnitHead head);

    void logUpdated(UnitHead newUnitHead);

    void logDeleted(UnitHead deletedUnitHead);

    List<UnitHeadLog> getAllLogs();

    List<UnitHeadLog> getLogsByUnitName(String unitName);

    List<UnitHeadLog> getLogsForOneDay(String dateString);

    List<UnitHeadLog> getLogsForCustomDays(String startDateString, String endDateString);
}
