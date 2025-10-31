package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.UnitHeadLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UnitHeadLogRepo extends JpaRepository<UnitHeadLog, Long> {

    List<UnitHeadLog> findByUnitName(String unitName);

    List<UnitHeadLog> findByLastUpdatedAtBetween(LocalDateTime start, LocalDateTime end);

}
