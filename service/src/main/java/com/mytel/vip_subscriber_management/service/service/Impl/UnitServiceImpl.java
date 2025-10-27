package com.mytel.vip_subscriber_management.service.service.Impl;


import com.mytel.vip_subscriber_management.common.exception.CommonException;
import com.mytel.vip_subscriber_management.database.entity.Unit;
import com.mytel.vip_subscriber_management.database.entity.UnitLog;
import com.mytel.vip_subscriber_management.database.repository.UnitRepo;
import com.mytel.vip_subscriber_management.service.service.UnitLogService;
import com.mytel.vip_subscriber_management.service.service.UnitService;
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
    public Unit createUnit(Unit unit) {
        if (repo.existsByUnitCode(unit.getUnitCode())) {
            throw new CommonException("Unit Code " + unit.getUnitCode() + " Already Exist! Please Change.");
        }
        LocalDateTime now = LocalDateTime.now();
        unit.setCreatedAt(Timestamp.valueOf(now));
        unit.setLastUpdatedAt(Timestamp.valueOf(now));
        Unit saved = repo.save(unit);
        service.logCreated(saved);
        return saved;
    }

    @Override
    public List<Unit> getAllUnit() {
        return repo.findAll();
    }

    @Override
    public Unit getUnitById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404", "Unit with ID " + id + " not found."));
    }

    @Override
    public List<Unit> getUnitByUnitFullName(String unitFullName) {
        List<Unit> results = repo.findByUnitFullName(unitFullName);
        if (results.isEmpty()) {
            throw new CommonException("Unit not found for: " + unitFullName);
        }
        return results;
    }

    @Override
    public Unit updateUnitByUnitCode(String unitCode, Unit updated) {
        Unit existingUnitHead = repo.findByUnitCode(unitCode)
                .orElseThrow(() -> new CommonException("ERR_404", "Unit with name " + unitCode + " not found."));

        Unit oldUnit = new Unit();
        BeanUtils.copyProperties(existingUnitHead, oldUnit);

        updated.setUnitCode(existingUnitHead.getUnitCode());

        if (isValid(updated.getUnitName())) {
            existingUnitHead.setUnitName(updated.getUnitName());
        }
        if (isValid(updated.getUnitFullName())) {
            existingUnitHead.setUnitFullName(updated.getUnitFullName());
        }
        if (isValid(updated.getEmail())) {
            existingUnitHead.setEmail(updated.getEmail());
        }
        if (isValid(updated.getPhoneNumber())) {
            existingUnitHead.setPhoneNumber(updated.getPhoneNumber());
        }
        if (isValid(updated.getRemark())) {
            existingUnitHead.setRemark(updated.getRemark());
        }
        Unit saved = repo.save(existingUnitHead);
        UnitLog log = service.logUpdated(oldUnit, saved);
        if (log != null && log.getLastUpdatedAt() != null) {
            saved.setLastUpdatedAt(log.getLastUpdatedAt());
        } else {
            saved.setLastUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        }

        return repo.save(saved);
    }

    @Override
    public void deleteUnit(Long id) {
        Unit existing = repo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404",
                        "Unit Head with ID " + id + " not found."));
        repo.delete(existing);
        service.logDeleted(existing);
    }

    private boolean isValid(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
