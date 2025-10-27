package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;


import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.ConfirmPageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmPageUserRepo extends JpaRepository<ConfirmPageUser, String> {
    Optional<ConfirmPageUser> findByIsdn(String isdn);
}
