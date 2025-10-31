package com.mytel.vip_subscriber_management.service.service;



import com.mytel.vip_subscriber_management.database.entity.Branch;
import com.mytel.vip_subscriber_management.database.entity.BranchLog;

import java.util.List;

public interface BranchLogService {

    void logCreated(Branch branch);

    BranchLog logUpdated(Branch oldBranch, Branch newBranch);

    void logDeleted(Branch deletedBranch);

    List<BranchLog> getAllLogs();

    List<BranchLog> getLogsByBranchName(String branchName);

    List<BranchLog> getLogsByBranchNameAndAction(String branchName, String actionType);
}
