package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.UnitHead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitHeadRepo extends JpaRepository<UnitHead, String> {

    Optional<UnitHead> findByEmail(String email);

    Optional<UnitHead> findByVmyCode(String vmyCode);

    Optional<UnitHead> findByPhoneNumber(String phoneNumber);

    Optional<UnitHead> findByUnitHeadFullName(String unitHeadFullName);
}
