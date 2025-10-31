package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepo extends JpaRepository<Unit, Long> {

    Optional<Unit> findByUnitName(String unitName);

    Optional<Unit> findByUnitCode(String unitCode);

    boolean existsByUnitCode(String unitCode);

    boolean existsByUnitName(String unitName);
}
