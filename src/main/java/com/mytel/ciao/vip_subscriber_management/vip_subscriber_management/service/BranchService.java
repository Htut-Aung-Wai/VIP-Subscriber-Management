package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;

import java.util.List;

public interface BranchService {

    Branch createBranch(Branch branch);

    List<Branch> getAllBranch();

    Branch getBranchById(Long id);

    List<Branch> getBranchByBranchManagerName(String branchManagerName);

    Branch updateBranchByBranchCode(String branchCode, Branch updated);

    void deleteBranch(Long id);
}
