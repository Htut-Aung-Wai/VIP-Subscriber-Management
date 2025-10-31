package com.mytel.vip_subscriber_management.service.service.Impl;

import com.mytel.vip_subscriber_management.common.exception.CommonException;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadResponseDto;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadUpdateDto;
import com.mytel.vip_subscriber_management.database.entity.Unit;
import com.mytel.vip_subscriber_management.database.entity.UnitHead;
import com.mytel.vip_subscriber_management.database.repository.UnitHeadRepo;
import com.mytel.vip_subscriber_management.database.repository.UnitRepo;
import com.mytel.vip_subscriber_management.service.service.UnitHeadLogService;
import com.mytel.vip_subscriber_management.service.service.UnitHeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitHeadServiceImpl implements UnitHeadService {

    private final UnitHeadRepo repo;
    private final UnitRepo unitRepo;
    private final UnitHeadLogService service;

    @Override
    @Transactional
    public UnitHead create(UnitHead unitHead, String unitCode) {
        unitHead.setEmail(unitHead.getEmail().trim().toLowerCase());
        unitHead.setPhoneNumber(unitHead.getPhoneNumber().trim());
        unitHead.setVmyCode(unitHead.getVmyCode().trim().toUpperCase());

        if (repo.findByEmail(unitHead.getEmail()).isPresent()) {
            throw new CommonException("ERR_409", "Email Already Exist!");
        }
        if (repo.findByVmyCode(unitHead.getVmyCode()).isPresent()) {
            throw new CommonException("ERR_409", "VMY CODE Already Exist!");
        }
        if (repo.findByPhoneNumber(unitHead.getPhoneNumber()).isPresent()) {
            throw new CommonException("ERR_409", "Phone Number Already Exist!");
        }
        Unit unit = unitRepo.findByUnitCode(unitCode).orElseThrow(() -> new CommonException("ERR_404", "Unit Code Not Found."));
        if (unit.getUnitHead() != null) {
            throw new CommonException("ERR_409", "This Unit already has Unit Head!");
        }
        unitHead.assignUnit(unit);
//        createdBy
        UnitHead saved = repo.save(unitHead);
        service.logCreated(saved);

        return saved;
    }

    @Override
    public UnitHeadResponseDto getByUnitHeadFullName(String unitHeadFullName) {
        UnitHead head = repo.findByUnitHeadFullName(unitHeadFullName).orElseThrow(() -> new CommonException("ERR_404", "Unit Head Not Found."));

        return new UnitHeadResponseDto(
                head.getId(),
                head.getUnitHeadFullName(),
                head.getVmyCode(),
                head.getEmail(),
                head.getPhoneNumber(),
                head.getRemark(),
                head.getUnit() != null ? head.getUnit().getUnitCode() : null,
                head.getUnit() != null ? head.getUnit().getUnitName() : null
        );
    }

    @Override
    public UnitHead getById(String id) {
        return repo.findById(id).orElseThrow(() -> new CommonException("ERR_404", "Unit ID Not Found."));
    }

    @Override
    public List<UnitHead> getAll() {
        return repo.findAll();
    }

    @Override
    @Transactional
    public UnitHead updateByUnitCode(UnitHeadUpdateDto dto, String unitCode) {
        Unit unit = unitRepo.findByUnitCode(unitCode).orElseThrow(() -> new CommonException("ERR_404", "Unit Code Not Found."));
        UnitHead existingUnitHead = unit.getUnitHead();
        if (existingUnitHead == null) {
            throw new CommonException("ERR_404", "Unit Head Not Found!");
        }
        existingUnitHead.setVmyCode(dto.getVmyCode());
        existingUnitHead.setUnitHeadFullName(dto.getUnitHeadFullName());
        existingUnitHead.setEmail(dto.getEmail());
        existingUnitHead.setPhoneNumber(dto.getPhoneNumber());
        existingUnitHead.setRemark(dto.getRemark());
//        createdBy
        UnitHead updated = repo.save(existingUnitHead);
        service.logUpdated(updated);

        return updated;
    }

    @Override
    public void deleteByUnitCode(String unitCode) {
        Unit unit = unitRepo.findByUnitCode(unitCode).orElseThrow(() -> new CommonException("ERR_404", "Unit Code Not Found."));
        UnitHead head = unit.getUnitHead();
        if (head == null) {
            throw new CommonException("ERR_404", "Unit Head Not Found.");
        }
        repo.delete(head);
        service.logDeleted(head);
    }

}
