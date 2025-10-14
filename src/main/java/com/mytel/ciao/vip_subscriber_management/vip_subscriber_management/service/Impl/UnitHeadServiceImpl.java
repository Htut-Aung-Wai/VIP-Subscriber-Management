package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHead;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.exception.CommonException;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHeadLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitHeadLogService;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.UnitHeadRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitHeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitHeadServiceImpl implements UnitHeadService {

    private final UnitHeadRepo unitRepo;
    private final UnitHeadLogService logService;

    @Override
    public UnitHead createUnitHead(UnitHead unitHead) {
        if (unitRepo.existsByUnitCode(unitHead.getUnitCode())) {
            throw new CommonException("Unit Code " + unitHead.getUnitCode() + " Already Exist! Please Change.");
        }
        LocalDateTime now = LocalDateTime.now();
        unitHead.setCreatedAt(now);
        unitHead.setLastUpdatedAt(now);
        UnitHead saved = unitRepo.save(unitHead);
        logService.logCreated(saved);
        return saved;
    }

    @Override
    public List<UnitHead> getAllUnitHead() {
        return unitRepo.findAll();
    }

    @Override
    public UnitHead getUnitHeadById(Long id) {
        return unitRepo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404", "Unit Head with ID " + id + " not found."));
    }

    @Override
    public List<UnitHead> getUnitHeadByUnitFullName(String unitFullName) {
        List<UnitHead> results = unitRepo.findByUnitFullName(unitFullName);
        if (results.isEmpty()) {
            throw new CommonException("Unit Head not found for: " + unitFullName);
        }
        return results;

    }

    @Override
    public UnitHead updateUnitHeadByUnitCode(String unitCode, UnitHead updated) {
        UnitHead existingUnitHead = unitRepo.findByUnitCode(unitCode)
                .orElseThrow(() -> new CommonException("ERR_404", "Unit Head with name " + unitCode + " not found."));

        UnitHead oldUnit = new UnitHead();
        BeanUtils.copyProperties(existingUnitHead, oldUnit);

//        if (updated.getUnitCode() != null && unitRepo.existsByUnitCode(updated.getUnitCode()) && !updated.getUnitCode().equals(existingUnitHead.getUnitCode())) {
//            throw new CommonException("Unit Code " + updated.getUnitCode() + " Already Exist! Please Change.");
//        }
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
        UnitHead saved = unitRepo.save(existingUnitHead);
        UnitHeadLog log = logService.logUpdated(oldUnit, saved);
        if (log != null && log.getLastUpdatedAt() != null) {
            saved.setLastUpdatedAt(log.getLastUpdatedAt());
        } else {
            saved.setLastUpdatedAt(LocalDateTime.now());
        }

        return unitRepo.save(saved);
    }

    @Override
    public void deleteUnitHead(Long id) {
        UnitHead existing = unitRepo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404",
                        "Unit Head with ID " + id + " not found."));
        unitRepo.delete(existing);
        logService.logDeleted(existing);
    }

    private boolean isValid(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
