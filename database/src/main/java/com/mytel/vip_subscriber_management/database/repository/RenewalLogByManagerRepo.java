package com.mytel.vip_subscriber_management.database.repository;


import com.mytel.vip_subscriber_management.database.entity.RenewByManagerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenewalLogByManagerRepo extends JpaRepository<RenewByManagerLog, Long> {
}
