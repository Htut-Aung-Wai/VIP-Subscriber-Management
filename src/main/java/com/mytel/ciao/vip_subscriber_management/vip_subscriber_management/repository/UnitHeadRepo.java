package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitHeadRepo extends JpaRepository<UnitHead, Long> {

    Optional<UnitHead> findByUnitName(String unitName);
}
