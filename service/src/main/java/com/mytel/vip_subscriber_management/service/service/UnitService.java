package com.mytel.vip_subscriber_management.service.service;


import com.mytel.vip_subscriber_management.database.entity.Branch;

import java.util.List;

public interface BranchService {

    Branch createBranch(Branch branch);

    List<Branch> getAllBranch();

    Branch getBranchById(Long id);

    List<Branch> getBranchByBranchManagerName(String branchManagerName);

    Branch updateBranchByBranchCode(String branchCode, Branch updated);

    void deleteBranch(Long id);
}
