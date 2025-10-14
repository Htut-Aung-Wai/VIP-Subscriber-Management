package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHead;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHeadLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.UnitHeadLogRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitHeadLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UnitHeadLogServiceImpl implements UnitHeadLogService {

    private final UnitHeadLogRepo repo;

    @Override
    public void logCreated(UnitHead unit) {
        UnitHeadLog log = new UnitHeadLog();
        log.setActionType(UnitHeadLog.ActionType.CREATED);
        log.setUnitCode(unit.getUnitCode());
        log.setUnitName(unit.getUnitName());
        log.setUnitFullName(unit.getUnitFullName());
        log.setEmail(unit.getEmail());
        log.setPhoneNumber(unit.getPhoneNumber());

        List<String> fieldValues = new ArrayList<>();
        if (unit.getUnitName() != null) fieldValues.add(unit.getUnitName());
        if (unit.getUnitFullName() != null) fieldValues.add(unit.getUnitFullName());
        if (unit.getEmail() != null) fieldValues.add(unit.getEmail());
        if (unit.getPhoneNumber() != null) fieldValues.add(unit.getPhoneNumber());

        String originalData = String.join(", ", fieldValues);

        log.setOriginalFields(originalData.trim());
        log.setChangedFields(null);

        repo.save(log);
    }

    @Override
    public UnitHeadLog logUpdated(UnitHead oldUnit, UnitHead newUnit) {
        UnitHeadLog log = new UnitHeadLog();
        log.setActionType(UnitHeadLog.ActionType.UPDATED);
        log.setUnitCode(oldUnit.getUnitCode());
        log.setUnitName(newUnit.getUnitName());
        log.setUnitFullName(newUnit.getUnitFullName());
        log.setEmail(newUnit.getEmail());
        log.setPhoneNumber(newUnit.getPhoneNumber());

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

        if (fieldNames.isEmpty()) {
            return null;
        }
        log.setOriginalFields(String.join(", ", originalValues));
        log.setChangedFields(String.join(", ", changedValues));

        return repo.save(log);
    }

    @Override
    public void logDeleted(UnitHead deletedUnit) {
        UnitHeadLog log = new UnitHeadLog();
        log.setActionType(UnitHeadLog.ActionType.DELETED);
        log.setUnitCode(deletedUnit.getUnitCode());

        log.setUnitName(deletedUnit.getUnitName());
        log.setUnitFullName(deletedUnit.getUnitFullName());
        log.setEmail(deletedUnit.getEmail());
        log.setPhoneNumber(deletedUnit.getPhoneNumber());

        log.setOriginalFields("Unit Head " + deletedUnit.getUnitFullName() + " deleted.");
        log.setChangedFields("Unit Head " + deletedUnit.getUnitFullName() + " deleted.");
        repo.save(log);
    }

    @Override
    public List<UnitHeadLog> getAllLogs() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public List<UnitHeadLog> getLogsByUnitName(String unitName) {
        return repo.findByUnitNameOrderByCreatedAtDesc(unitName);
    }

    @Override
    public List<UnitHeadLog> getLogsByUnitNameAndAction(String unitName, UnitHeadLog.ActionType actionType) {
        return repo.findByUnitNameAndActionTypeOrderByCreatedAtDesc(unitName, actionType);
    }

}
