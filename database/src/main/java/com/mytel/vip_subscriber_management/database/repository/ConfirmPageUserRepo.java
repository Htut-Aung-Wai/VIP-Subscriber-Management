package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.ConfirmPageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmPageUserRepo extends JpaRepository<ConfirmPageUser, String> {

    Optional<ConfirmPageUser> findByIsdn(String isdn);
}
