package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.repo;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.entity.RenewByManagerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenewalLogByManagerRepo extends JpaRepository<RenewByManagerLog, Long> {
}
