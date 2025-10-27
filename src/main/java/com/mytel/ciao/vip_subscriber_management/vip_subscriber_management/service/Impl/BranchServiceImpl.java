package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.exception.CommonException;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.BranchLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.BranchLogService;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.BranchRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepo repo;
    private final BranchLogService service;

    @Override
    public Branch createBranch(Branch branch) {
        if (repo.existsByBranchCode(branch.getBranchCode())) {
            throw new CommonException("Branch Code " + branch.getBranchCode() + " Already Exist! Please Change.");
        }
        LocalDateTime now = LocalDateTime.now();
        branch.setCreatedAt(Timestamp.valueOf(now));
        branch.setLastUpdatedAt(Timestamp.valueOf(now));
        Branch saved = repo.save(branch);
        service.logCreated(saved);
        return saved;
    }

    @Override
    public List<Branch> getAllBranch() {
        return repo.findAll();
    }

    @Override
    public Branch getBranchById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404", "Branch with ID " + id + " not found."));
    }

    @Override
    public List<Branch> getBranchByBranchManagerName(String branchManagerName) {
        List<Branch> results = repo.findByBranchManagerName(branchManagerName);
        if (results.isEmpty()) {
            throw new CommonException("Branch not found for: " + branchManagerName);
        }
        return results;
    }

    @Override
    public Branch updateBranchByBranchCode(String branchCode, Branch updated) {
        Branch existingBranch = repo.findByBranchCode(branchCode)
                .orElseThrow(() -> new CommonException("ERR_404", "Branch with name " + branchCode + " not found."));

        Branch oldBranch = new Branch();
        BeanUtils.copyProperties(existingBranch, oldBranch);

        updated.setBranchCode(existingBranch.getBranchCode());

        if (isValid(updated.getBranchName())) {
            existingBranch.setBranchName(updated.getBranchName());
        }
        if (isValid(updated.getBranchManagerName())) {
            existingBranch.setBranchManagerName(updated.getBranchManagerName());
        }
        if (isValid(updated.getEmail())) {
            existingBranch.setEmail(updated.getEmail());
        }
        if (isValid(updated.getPhoneNumber())) {
            existingBranch.setPhoneNumber(updated.getPhoneNumber());
        }
        if (isValid(updated.getRemark())) {
            existingBranch.setRemark(updated.getRemark());
        }
        Branch saved = repo.save(existingBranch);
        BranchLog log = service.logUpdated(oldBranch, saved);
        if (log != null && log.getLastUpdatedAt() != null) {
            saved.setLastUpdatedAt(log.getLastUpdatedAt());
        } else {
            saved.setLastUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        }

        return repo.save(saved);
    }

    @Override
    public void deleteBranch(Long id) {
        Branch existing = repo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404",
                        "Branch with ID " + id + " not found."));
        repo.delete(existing);
        service.logDeleted(existing);
    }

    private boolean isValid(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
