package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriber;
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

    @Query("SELECT v FROM VipSubscriber v WHERE v.isDeleted = false")
    List<VipSubscriber> findAllActive();

    /**
     * For Expiring Subscribers In Month N+2
     */
    @Query("SELECT s FROM VipSubscriber s WHERE s.expiryDate BETWEEN :start AND :end AND s.branch = :branch")
    List<VipSubscriber> findExpiringSubscribersByBranchName(@Param("start") LocalDateTime start,
                                                            @Param("end") LocalDateTime end,
                                                            @Param("branch") String branch);

}
