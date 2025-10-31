package com.mytel.vip_subscriber_management.database.repository;


import com.mytel.vip_subscriber_management.database.entity.BranchLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchLogRepo extends JpaRepository<BranchLog, Long> {

    List<BranchLog> findByBranchName(String branchName);

    List<BranchLog> findByBranchNameAndAction(String branchName, String actionType);

}
