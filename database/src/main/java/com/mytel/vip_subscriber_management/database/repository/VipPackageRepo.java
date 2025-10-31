package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.VipPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VipPackageRepo extends JpaRepository<VipPackage, Long> {
}
