package com.mytel.vip_subscriber_management.database.repository;


import com.mytel.vip_subscriber_management.database.entity.VipSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
    @Query("SELECT s FROM VipSubscriber s WHERE s.expiryDate BETWEEN :start AND :end AND s.branchName = :branchName")
    List<VipSubscriber> findExpiringSubscribersByBranchName(@Param("start") Timestamp start,
                                                            @Param("end") Timestamp end,
                                                            @Param("branchName") String branchName);

    @Query("SELECT v.subscriberNo FROM VipSubscriber v WHERE v.isDeleted = false")
    List<String> findAllSubscriberNumbers();

}
