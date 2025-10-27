package com.mytel.vip_subscriber_management.service.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.BranchLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.BranchLogRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.BranchLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BranchLogServiceImpl implements BranchLogService {

    private final BranchLogRepo repo;

    @Override
    public void logCreated(Branch branch) {
        BranchLog log = new BranchLog();
        log.setAction("CREATED");
        log.setBranchCode(branch.getBranchCode());
        log.setBranchName(branch.getBranchName());
        log.setBranchManagerName(branch.getBranchManagerName());
        log.setEmail(branch.getEmail());
        log.setPhoneNumber(branch.getPhoneNumber());
        log.setRemark(branch.getRemark());

        List<String> fieldValues = new ArrayList<>();
        if (branch.getBranchName() != null) fieldValues.add(branch.getBranchName());
        if (branch.getBranchManagerName() != null) fieldValues.add(branch.getBranchManagerName());
        if (branch.getEmail() != null) fieldValues.add(branch.getEmail());
        if (branch.getPhoneNumber() != null) fieldValues.add(branch.getPhoneNumber());
        if (branch.getRemark() != null) {
            fieldValues.add(branch.getRemark());
        } else {
            fieldValues.add(null);
        }

        String originalData = String.join(", ", fieldValues);

        log.setOriginalFields(originalData.trim());
        log.setUpdatedFields(null);

        repo.save(log);
    }

    @Override
    public BranchLog logUpdated(Branch oldBranch, Branch newBranch) {
        BranchLog log = new BranchLog();
        log.setAction("UPDATED");
        log.setBranchCode(newBranch.getBranchCode());
        log.setBranchName(newBranch.getBranchName());
        log.setBranchManagerName(newBranch.getBranchManagerName());
        log.setEmail(newBranch.getEmail());
        log.setPhoneNumber(newBranch.getPhoneNumber());
        log.setRemark(newBranch.getRemark());

        List<String> fieldNames = new ArrayList<>();
        List<String> originalValues = new ArrayList<>();
        List<String> changedValues = new ArrayList<>();

        if (!Objects.equals(oldBranch.getBranchName(), newBranch.getBranchName())) {
            fieldNames.add("branchName");
            originalValues.add(String.valueOf(oldBranch.getBranchName()));
            changedValues.add(String.valueOf(newBranch.getBranchName()));
        }

        if (!Objects.equals(oldBranch.getBranchManagerName(), newBranch.getBranchManagerName())) {
            fieldNames.add("branchManagerName");
            originalValues.add(String.valueOf(oldBranch.getBranchManagerName()));
            changedValues.add(String.valueOf(newBranch.getBranchManagerName()));
        }

        if (!Objects.equals(oldBranch.getEmail(), newBranch.getEmail())) {
            fieldNames.add("email");
            originalValues.add(String.valueOf(oldBranch.getEmail()));
            changedValues.add(String.valueOf(newBranch.getEmail()));
        }

        if (!Objects.equals(oldBranch.getPhoneNumber(), newBranch.getPhoneNumber())) {
            fieldNames.add("phoneNumber");
            originalValues.add(String.valueOf(oldBranch.getPhoneNumber()));
            changedValues.add(String.valueOf(newBranch.getPhoneNumber()));
        }

        if (!Objects.equals(oldBranch.getRemark(), newBranch.getRemark())) {
            fieldNames.add("remark");
            originalValues.add(String.valueOf(oldBranch.getRemark()));
            changedValues.add(String.valueOf(newBranch.getRemark()));
        }

        if (fieldNames.isEmpty()) {
            return null;
        }
        log.setOriginalFields(String.join(", ", originalValues));
        log.setUpdatedFields(String.join(", ", changedValues));

        return repo.save(log);
    }

    @Override
    public void logDeleted(Branch deletedBranch) {
        BranchLog log = new BranchLog();
        log.setAction("DELETED");
        log.setBranchCode(deletedBranch.getBranchCode());
        log.setBranchName(deletedBranch.getBranchName());
        log.setBranchManagerName(deletedBranch.getBranchManagerName());
        log.setEmail(deletedBranch.getEmail());
        log.setPhoneNumber(deletedBranch.getPhoneNumber());
        log.setRemark((deletedBranch.getRemark()));

        log.setOriginalFields("Branch " + deletedBranch.getBranchManagerName() + " deleted.");
        log.setUpdatedFields("Branch " + deletedBranch.getBranchManagerName() + " deleted.");
        repo.save(log);
    }

    @Override
    public List<BranchLog> getAllLogs() {
        return repo.findAll();
    }

    @Override
    public List<BranchLog> getLogsByBranchName(String branchName) {
        return repo.findByBranchName(branchName);
    }

    @Override
    public List<BranchLog> getLogsByBranchNameAndAction(String branchName, String actionType) {
        return repo.findByBranchNameAndAction(branchName, actionType);
    }

}
