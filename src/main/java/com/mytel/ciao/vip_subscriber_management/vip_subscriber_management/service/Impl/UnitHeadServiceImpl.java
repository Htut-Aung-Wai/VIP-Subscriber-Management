package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHead;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.exception.CommonException;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.UnitHeadRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitHeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitHeadServiceImpl implements UnitHeadService {

    private final UnitHeadRepo unitRepo;

    @Override
    public UnitHead createUnitHead(UnitHead unitHead) {
        return unitRepo.save(unitHead);
    }

    @Override
    public List<UnitHead> getAllUnitHead() {
        return unitRepo.findAll();
    }

    @Override
    public UnitHead getUnitHeadById(Long id) {
        return unitRepo.findById(id).orElseThrow(() -> new CommonException("ERR_404", "Unit Head with ID " + id + " not found."));
    }

    @Override
    public UnitHead updateUnitHeadByUnitName(String existingUnitName, UnitHead updated) {
        UnitHead existingUnitHead = unitRepo.findByUnitName(existingUnitName).orElseThrow(() -> new CommonException("ERR_404", "Unit Head with name " + existingUnitName + " not found."));

        if (updated.getUnitCode() != null && !updated.getUnitCode().isEmpty()) {
            existingUnitHead.setUnitCode(updated.getUnitCode());
        }
        if (updated.getUnitName() != null && !updated.getUnitName().isEmpty()) {
            existingUnitHead.setUnitName(updated.getUnitName());
        }
        if (updated.getUnitFullName() != null && !updated.getUnitFullName().isEmpty()) {
            existingUnitHead.setUnitFullName(updated.getUnitFullName());
        }
        if (updated.getEmail() != null && !updated.getEmail().isEmpty()) {
            existingUnitHead.setEmail(updated.getEmail());
        }
        if (updated.getPhoneNumber() != null && !updated.getPhoneNumber().isEmpty()) {
            existingUnitHead.setPhoneNumber(updated.getPhoneNumber());
        }
        return unitRepo.save(existingUnitHead);
    }

    @Override
    public void deleteUnitHead(Long id) {
        if (!unitRepo.existsById(id)) {
            throw new CommonException("ERR_404", "Unit Head with ID " + id + " not found.");
        }
        unitRepo.deleteById(id);
    }
}
