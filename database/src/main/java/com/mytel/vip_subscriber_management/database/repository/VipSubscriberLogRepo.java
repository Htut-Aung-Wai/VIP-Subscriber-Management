package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.VipSubscriberLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VipSubscriberLogRepo extends JpaRepository<VipSubscriberLog, Long> {
}
