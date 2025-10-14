package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHeadLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitHeadLogRepo extends JpaRepository<UnitHeadLog, Long> {

    List<UnitHeadLog> findByUnitName(String unitName);

    List<UnitHeadLog> findByUnitNameAndActionType(String unitName, UnitHeadLog.ActionType actionType);

}
