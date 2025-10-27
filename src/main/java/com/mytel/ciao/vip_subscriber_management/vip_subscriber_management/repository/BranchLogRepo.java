package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitLogRepo extends JpaRepository<UnitLog, Long> {

    List<UnitLog> findByUnitName(String unitName);

    List<UnitLog> findByUnitNameAndAction(String unitName, String actionType);

}
