package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.VipSubscriberImportTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VipSubscriberImportTempRepo extends JpaRepository<VipSubscriberImportTemp, String> {
    List<VipSubscriberImportTemp> findByToken(String token);
    void deleteByToken(String token);
}
