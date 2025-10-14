package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.logging.repo;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.logging.entity.UnitHeadLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitHeadLogRepo extends JpaRepository<UnitHeadLog, Long> {

    List<UnitHeadLog> findByUnitNameOrderByCreatedAtDesc(String unitName);

    List<UnitHeadLog> findByUnitNameAndActionTypeOrderByCreatedAtDesc(String unitName, UnitHeadLog.ActionType actionType);

}
