package com.mytel.vip_subscriber_management.database.repository;


import com.mytel.vip_subscriber_management.database.entity.UnitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitLogRepo extends JpaRepository<UnitLog, Long> {

    List<UnitLog> findByUnitName(String unitName);

    List<UnitLog> findByUnitNameAndAction(String unitName, String actionType);

}
