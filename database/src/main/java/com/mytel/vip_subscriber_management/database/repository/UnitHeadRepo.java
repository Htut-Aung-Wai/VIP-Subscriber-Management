package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.UnitHead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UnitHeadRepo extends JpaRepository<UnitHead, String> {

    /* For Unit Head Creation Validations */
    Optional<UnitHead> findByEmail(String email);

    Optional<UnitHead> findByVmyCode(String vmyCode);

    Optional<UnitHead> findByPhoneNumber(String phoneNumber);

    Optional<UnitHead> findByUnitHeadFullName(String unitHeadFullName);

    /* For Custom Search */
    @Query("SELECT uh FROM UnitHead uh " +
            "JOIN uh.unit u " +
            "WHERE LOWER(u.unitCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.unitName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    UnitHead findByUnitCodeIgnoreCaseOrUnitNameIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT uh FROM UnitHead uh WHERE uh.phoneNumber = :phoneNumber")
    UnitHead findByPhone(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT uh FROM UnitHead uh WHERE LOWER(uh.vmyCode) = LOWER(:vmyCode)")
    UnitHead findByVmyCodeIgnoreCase(@Param("vmyCode") String vmyCode);

    Page<UnitHead> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

}
