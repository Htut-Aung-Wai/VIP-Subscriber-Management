package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.UnitLogRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UnitLogServiceImpl implements UnitLogService {

    private final UnitLogRepo repo;

    @Override
    public void logCreated(Branch branch) {
        UnitLog log = new UnitLog();
        log.setAction("CREATED");
        log.setUnitCode(branch.getUnitCode());
        log.setUnitName(branch.getUnitName());
        log.setUnitHeadFullName(branch.getUnitHeadFullName());
        log.setEmail(branch.getEmail());
        log.setPhoneNumber(branch.getPhoneNumber());
        log.setRemark(branch.getRemark());

        List<String> fieldValues = new ArrayList<>();
        if (branch.getUnitName() != null) fieldValues.add(branch.getUnitName());
        if (branch.getUnitHeadFullName() != null) fieldValues.add(branch.getUnitHeadFullName());
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
    public UnitLog logUpdated(Branch oldBranch, Branch newBranch) {
        UnitLog log = new UnitLog();
        log.setAction("UPDATED");
        log.setUnitCode(oldBranch.getUnitCode());
        log.setUnitName(newBranch.getUnitName());
        log.setUnitHeadFullName(newBranch.getUnitHeadFullName());
        log.setEmail(newBranch.getEmail());
        log.setPhoneNumber(newBranch.getPhoneNumber());
        log.setRemark(newBranch.getRemark());

        List<String> fieldNames = new ArrayList<>();
        List<String> originalValues = new ArrayList<>();
        List<String> changedValues = new ArrayList<>();

        if (!Objects.equals(oldBranch.getUnitName(), newBranch.getUnitName())) {
            fieldNames.add("unitName");
            originalValues.add(String.valueOf(oldBranch.getUnitName()));
            changedValues.add(String.valueOf(newBranch.getUnitName()));
        }

        if (!Objects.equals(oldBranch.getUnitHeadFullName(), newBranch.getUnitHeadFullName())) {
            fieldNames.add("unitHeadFullName");
            originalValues.add(String.valueOf(oldBranch.getUnitHeadFullName()));
            changedValues.add(String.valueOf(newBranch.getUnitHeadFullName()));
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
        UnitLog log = new UnitLog();
        log.setAction("DELETED");
        log.setUnitCode(deletedBranch.getUnitCode());
        log.setUnitName(deletedBranch.getUnitName());
        log.setUnitHeadFullName(deletedBranch.getUnitHeadFullName());
        log.setEmail(deletedBranch.getEmail());
        log.setPhoneNumber(deletedBranch.getPhoneNumber());
        log.setRemark((deletedBranch.getRemark()));

        log.setOriginalFields("Unit " + deletedBranch.getUnitHeadFullName() + " deleted.");
        log.setUpdatedFields("Unit " + deletedBranch.getUnitHeadFullName() + " deleted.");
        repo.save(log);
    }

    @Override
    public List<UnitLog> getAllLogs() {
        return repo.findAll();
    }

    @Override
    public List<UnitLog> getLogsByUnitName(String unitName) {
        return repo.findByUnitName(unitName);
    }

    @Override
    public List<UnitLog> getLogsByUnitNameAndAction(String unitName, String actionType) {
        return repo.findByUnitNameAndAction(unitName, actionType);
    }

}
