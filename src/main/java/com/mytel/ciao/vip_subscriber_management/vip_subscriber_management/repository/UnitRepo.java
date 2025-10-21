package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepo extends JpaRepository<Unit, Long> {

    List<Unit> findByUnitName(String unitName);

    List<Unit> findByUnitFullName(String unitFullName);

    Optional<Unit> findByUnitCode(String unitCode);

    boolean existsByUnitCode(String unitCode);

}
