package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByVmyCode(String vmyCode);

    Optional<User> findByPhoneNumber(String phoneNumber);

}

