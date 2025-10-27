package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;


import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, String> {

    List<Otp> findByIsdnAndRequestTimeAfter(String isdn, LocalDateTime time);

    Optional<Otp> findByOtpAndIsdn(String otp, String isdn);
}
