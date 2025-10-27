package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.exception.CommonException;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitLogService;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.UnitRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepo repo;
    private final UnitLogService service;

    @Override
    public Branch createUnit(Branch branch) {
        if (repo.existsByUnitCode(branch.getUnitCode())) {
            throw new CommonException("Unit Code " + branch.getUnitCode() + " Already Exist! Please Change.");
        }
        LocalDateTime now = LocalDateTime.now();
        branch.setCreatedAt(Timestamp.valueOf(now));
        branch.setLastUpdatedAt(Timestamp.valueOf(now));
        Branch saved = repo.save(branch);
        service.logCreated(saved);
        return saved;
    }

    @Override
    public List<Branch> getAllUnit() {
        return repo.findAll();
    }

    @Override
    public Branch getUnitById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404", "Unit with ID " + id + " not found."));
    }

    @Override
    public List<Branch> getUnitByUnitHeadFullName(String unitHeadFullName) {
        List<Branch> results = repo.findByUnitHeadFullName(unitHeadFullName);
        if (results.isEmpty()) {
            throw new CommonException("Unit not found for: " + unitHeadFullName);
        }
        return results;
    }

    @Override
    public Branch updateUnitByUnitCode(String unitCode, Branch updated) {
        Branch existingBranchHead = repo.findByUnitCode(unitCode)
                .orElseThrow(() -> new CommonException("ERR_404", "Unit with name " + unitCode + " not found."));

        Branch oldBranch = new Branch();
        BeanUtils.copyProperties(existingBranchHead, oldBranch);

        updated.setUnitCode(existingBranchHead.getUnitCode());

        if (isValid(updated.getUnitName())) {
            existingBranchHead.setUnitName(updated.getUnitName());
        }
        if (isValid(updated.getUnitHeadFullName())) {
            existingBranchHead.setUnitHeadFullName(updated.getUnitHeadFullName());
        }
        if (isValid(updated.getEmail())) {
            existingBranchHead.setEmail(updated.getEmail());
        }
        if (isValid(updated.getPhoneNumber())) {
            existingBranchHead.setPhoneNumber(updated.getPhoneNumber());
        }
        if (isValid(updated.getRemark())) {
            existingBranchHead.setRemark(updated.getRemark());
        }
        Branch saved = repo.save(existingBranchHead);
        UnitLog log = service.logUpdated(oldBranch, saved);
        if (log != null && log.getLastUpdatedAt() != null) {
            saved.setLastUpdatedAt(log.getLastUpdatedAt());
        } else {
            saved.setLastUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        }

        return repo.save(saved);
    }

    @Override
    public void deleteUnit(Long id) {
        Branch existing = repo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404",
                        "Unit Head with ID " + id + " not found."));
        repo.delete(existing);
        service.logDeleted(existing);
    }

    private boolean isValid(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
