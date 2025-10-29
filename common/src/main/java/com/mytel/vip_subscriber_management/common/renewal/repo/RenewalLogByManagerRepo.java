package com.mytel.vip_subscriber_management.common.renewal.repo;

import com.example.vip_management.renewal.entity.RenewByManagerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenewalLogByManagerRepo extends JpaRepository<RenewByManagerLog, Long> {
}
