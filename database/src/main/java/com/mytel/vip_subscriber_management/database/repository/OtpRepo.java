package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.Otp;
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
