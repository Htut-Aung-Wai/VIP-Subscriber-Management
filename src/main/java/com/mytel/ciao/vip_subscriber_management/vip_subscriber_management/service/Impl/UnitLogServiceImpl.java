package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Unit;
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
    public void logCreated(Unit unit) {
        UnitLog log = new UnitLog();
        log.setAction(UnitLog.ActionType.CREATED);
        log.setUnitCode(unit.getUnitCode());
        log.setUnitName(unit.getUnitName());
        log.setUnitFullName(unit.getUnitFullName());
        log.setEmail(unit.getEmail());
        log.setPhoneNumber(unit.getPhoneNumber());
        log.setRemark(unit.getRemark());

        List<String> fieldValues = new ArrayList<>();
        if (unit.getUnitName() != null) fieldValues.add(unit.getUnitName());
        if (unit.getUnitFullName() != null) fieldValues.add(unit.getUnitFullName());
        if (unit.getEmail() != null) fieldValues.add(unit.getEmail());
        if (unit.getPhoneNumber() != null) fieldValues.add(unit.getPhoneNumber());
        if (unit.getRemark() != null) {
            fieldValues.add(unit.getRemark());
        } else {
            fieldValues.add(null);
        }

        String originalData = String.join(", ", fieldValues);

        log.setOriginalFields(originalData.trim());
        log.setUpdatedFields(null);

        repo.save(log);
    }

    @Override
    public UnitLog logUpdated(Unit oldUnit, Unit newUnit) {
        UnitLog log = new UnitLog();
        log.setAction(UnitLog.ActionType.UPDATED);
        log.setUnitCode(oldUnit.getUnitCode());
        log.setUnitName(newUnit.getUnitName());
        log.setUnitFullName(newUnit.getUnitFullName());
        log.setEmail(newUnit.getEmail());
        log.setPhoneNumber(newUnit.getPhoneNumber());
        log.setRemark(newUnit.getRemark());

        List<String> fieldNames = new ArrayList<>();
        List<String> originalValues = new ArrayList<>();
        List<String> changedValues = new ArrayList<>();

        if (!Objects.equals(oldUnit.getUnitName(), newUnit.getUnitName())) {
            fieldNames.add("unitName");
            originalValues.add(String.valueOf(oldUnit.getUnitName()));
            changedValues.add(String.valueOf(newUnit.getUnitName()));
        }

        if (!Objects.equals(oldUnit.getUnitFullName(), newUnit.getUnitFullName())) {
            fieldNames.add("unitFullName");
            originalValues.add(String.valueOf(oldUnit.getUnitFullName()));
            changedValues.add(String.valueOf(newUnit.getUnitFullName()));
        }

        if (!Objects.equals(oldUnit.getEmail(), newUnit.getEmail())) {
            fieldNames.add("email");
            originalValues.add(String.valueOf(oldUnit.getEmail()));
            changedValues.add(String.valueOf(newUnit.getEmail()));
        }

        if (!Objects.equals(oldUnit.getPhoneNumber(), newUnit.getPhoneNumber())) {
            fieldNames.add("phoneNumber");
            originalValues.add(String.valueOf(oldUnit.getPhoneNumber()));
            changedValues.add(String.valueOf(newUnit.getPhoneNumber()));
        }

        if (!Objects.equals(oldUnit.getRemark(), newUnit.getRemark())) {
            fieldNames.add("remark");
            originalValues.add(String.valueOf(oldUnit.getRemark()));
            changedValues.add(String.valueOf(newUnit.getRemark()));
        }

        if (fieldNames.isEmpty()) {
            return null;
        }
        log.setOriginalFields(String.join(", ", originalValues));
        log.setUpdatedFields(String.join(", ", changedValues));

        return repo.save(log);
    }

    @Override
    public void logDeleted(Unit deletedUnit) {
        UnitLog log = new UnitLog();
        log.setAction(UnitLog.ActionType.DELETED);
        log.setUnitCode(deletedUnit.getUnitCode());
        log.setUnitName(deletedUnit.getUnitName());
        log.setUnitFullName(deletedUnit.getUnitFullName());
        log.setEmail(deletedUnit.getEmail());
        log.setPhoneNumber(deletedUnit.getPhoneNumber());
        log.setRemark((deletedUnit.getRemark()));

        log.setOriginalFields("Unit " + deletedUnit.getUnitFullName() + " deleted.");
        log.setUpdatedFields("Unit " + deletedUnit.getUnitFullName() + " deleted.");
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
    public List<UnitLog> getLogsByUnitNameAndAction(String unitName, UnitLog.ActionType actionType) {
        return repo.findByUnitNameAndActionType(unitName, actionType);
    }

}
