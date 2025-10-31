package com.mytel.vip_subscriber_management.database.repository;

import com.mytel.vip_subscriber_management.database.entity.VipSubscriber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VipSubscriberRepo extends JpaRepository<VipSubscriber, String> {


    @Query("SELECT v FROM VipSubscriber v WHERE v.id = :id AND v.isDeleted = false")
    Optional<VipSubscriber> findByIdAndNotDeleted(@Param("id") String id);

    /**
     * For Expiring Subscribers In Month N+2
     */
    @Query("SELECT s FROM VipSubscriber s WHERE s.expiryDate BETWEEN :start AND :end AND s.branchName = :branchName")
    List<VipSubscriber> findExpiringSubscribersByUnitName(@Param("start") LocalDateTime start,
                                                          @Param("end") LocalDateTime end,
                                                          @Param("branchName") String branchName);

    @Query("SELECT v.subscriberNo FROM VipSubscriber v WHERE v.isDeleted = false")
    List<String> findAllSubscriberNumbers();


    @Query("SELECT v FROM VipSubscriber v " +
            "WHERE v.isDeleted = false " +
            "AND ( " +
            "   :keyword IS NULL OR ( " +
            "       LOWER(v.subscriberNo) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "       OR LOWER(v.branchName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "       OR LOWER(v.proposalDocumentNo) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   ) " +
            ") " +
            "AND ( :subscriberNo IS NULL OR LOWER(v.subscriberNo) LIKE LOWER(CONCAT('%', :subscriberNo, '%')) ) " +
            "AND ( :branchName IS NULL OR LOWER(v.branchName) LIKE LOWER(CONCAT('%', :branchName, '%')) ) " +
            "AND ( :proposalDocumentNo IS NULL OR LOWER(v.proposalDocumentNo) LIKE LOWER(CONCAT('%', :proposalDocumentNo, '%')) ) " +
            "AND ( " +
            "   (:fromDate IS NULL OR :toDate IS NULL) " +
            "   OR (v.createdAt BETWEEN :fromDate AND :toDate) " +
            ")")
    Page<VipSubscriber> findActiveSubscriberWithFilterCombined(
            @Param("keyword") String keyword,
            @Param("subscriberNo") String subscriberNo,
            @Param("branchName") String branchName,
            @Param("proposalDocumentNo") String proposalDocumentNo,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
