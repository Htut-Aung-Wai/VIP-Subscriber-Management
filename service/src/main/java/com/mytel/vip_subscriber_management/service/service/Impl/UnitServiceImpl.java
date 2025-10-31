package com.mytel.vip_subscriber_management.service.service.Impl;

import com.mytel.vip_subscriber_management.common.exception.CommonException;
import com.mytel.vip_subscriber_management.database.entity.Unit;
import com.mytel.vip_subscriber_management.database.repository.UnitRepo;
import com.mytel.vip_subscriber_management.service.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepo repo;

    @Override
    @Transactional
    public Unit createUnit(Unit unit) {
        if (repo.existsByUnitCode(unit.getUnitCode())) {
            throw new CommonException("Unit Code " + unit.getUnitCode() + " Already Exist! Please Change.");
        }
        if (repo.existsByUnitName(unit.getUnitName())) {
            throw new CommonException("Unit Name " + unit.getUnitName() + " Already Exist! Please Change.");
        }
        return repo.save(unit);
    }

    @Override
    public List<Unit> getAllUnit() {
        return repo.findAll();
    }

    @Override
    public Unit getUnitById(Long id) {
        return repo.findById(id).orElseThrow(() -> new CommonException("ERR_404", "Unit With ID " + id + " Not Found."));
    }

    @Override
    public Unit getUnitByUnitName(String unitName) {
        return repo.findByUnitName(unitName).orElseThrow(() -> new CommonException("ERR_404", "Unit " + unitName + " Not Found."));
    }

    @Override
    public Unit getUnitByUnitCode(String unitCode) {
        return repo.findByUnitCode(unitCode).orElseThrow(() -> new CommonException("ERR_404", "Unit Code " + unitCode + " Not Found."));
    }

    @Override
    @Transactional
    public Unit updateUnitByUnitCode(String unitCode, Unit updated) {
        Unit existingUnit = repo.findByUnitCode(unitCode).orElseThrow(() -> new CommonException("ERR_404", "Unit Code " + unitCode + " Not Found."));

        updated.setUnitCode(existingUnit.getUnitCode());

        if (!isBlank(updated.getUnitName())) {
            existingUnit.setUnitName(updated.getUnitName());
        }
        existingUnit.setIsActive(updated.getIsActive());

        return repo.save(existingUnit);
    }

    @Override
    public void deleteUnit(Long id) {
        Unit existing = repo.findById(id).orElseThrow(() -> new CommonException("ERR_404", "Unit with ID " + id + " not found."));
        repo.delete(existing);
    }
}
