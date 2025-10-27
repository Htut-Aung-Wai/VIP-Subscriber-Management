package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.BranchLog;

import java.util.List;

public interface BranchLogService {

    void logCreated(Branch branch);

    BranchLog logUpdated(Branch oldBranch, Branch newBranch);

    void logDeleted(Branch deletedBranch);

    List<BranchLog> getAllLogs();

    List<BranchLog> getLogsByBranchName(String branchName);

    List<BranchLog> getLogsByBranchNameAndAction(String branchName, String actionType);
}
