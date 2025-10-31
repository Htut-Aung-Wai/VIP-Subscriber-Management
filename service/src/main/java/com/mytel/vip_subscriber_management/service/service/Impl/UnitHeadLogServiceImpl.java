package com.mytel.vip_subscriber_management.service.service.Impl;

import com.mytel.vip_subscriber_management.database.entity.UnitHead;
import com.mytel.vip_subscriber_management.database.entity.UnitHeadLog;
import com.mytel.vip_subscriber_management.database.repository.UnitHeadLogRepo;
import com.mytel.vip_subscriber_management.service.service.UnitHeadLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitHeadLogServiceImpl implements UnitHeadLogService {

    private final UnitHeadLogRepo repo;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    @Override
    public void logCreated(UnitHead head) {
        UnitHeadLog log = new UnitHeadLog();
        log.setUnitCode(head.getUnit().getUnitCode());
        log.setUnitName(head.getUnit().getUnitName());
        log.setUnitHeadFullName(head.getUnitHeadFullName());
        log.setVmyCode(head.getVmyCode());
        log.setEmail(head.getEmail());
        log.setPhoneNumber(head.getPhoneNumber());
        /* createdBy */
        log.setRemark(head.getRemark());

//        List<String> fieldValues = new ArrayList<>();
//        if (unit.getUnitName() != null) fieldValues.add(unit.getUnitName());
//        if (unit.getBranchManagerName() != null) fieldValues.add(unit.getBranchManagerName());
//        if (unit.getEmail() != null) fieldValues.add(unit.getEmail());
//        if (unit.getPhoneNumber() != null) fieldValues.add(unit.getPhoneNumber());
//        if (unit.getRemark() != null) {
//            fieldValues.add(unit.getRemark());
//        } else {
//            fieldValues.add(null);
//        }
//
//        String originalData = String.join(", ", fieldValues);
//
//        log.setOriginalFields(originalData.trim());
//        log.setUpdatedFields(null);

        repo.save(log);
    }

    @Override
    public void logUpdated(UnitHead newUnitHead) {
        UnitHeadLog log = new UnitHeadLog();
        log.setUnitCode(newUnitHead.getUnit().getUnitCode());
        log.setUnitName(newUnitHead.getUnit().getUnitName());
        log.setUnitHeadFullName(newUnitHead.getUnitHeadFullName());
        log.setVmyCode(newUnitHead.getVmyCode());
        log.setEmail(newUnitHead.getEmail());
        log.setPhoneNumber(newUnitHead.getPhoneNumber());
        /* createdBy */
        log.setRemark(newUnitHead.getRemark());

//        List<String> fieldNames = new ArrayList<>();
//        List<String> originalValues = new ArrayList<>();
//        List<String> changedValues = new ArrayList<>();
//
//        if (!Objects.equals(oldUnit.getUnitName(), newUnit.getUnitName())) {
//            fieldNames.add("branchName");
//            originalValues.add(String.valueOf(oldUnit.getUnitName()));
//            changedValues.add(String.valueOf(newUnit.getUnitName()));
//        }
//
//        if (!Objects.equals(oldUnit.getBranchManagerName(), newUnit.getBranchManagerName())) {
//            fieldNames.add("branchManagerName");
//            originalValues.add(String.valueOf(oldUnit.getBranchManagerName()));
//            changedValues.add(String.valueOf(newUnit.getBranchManagerName()));
//        }
//
//        if (!Objects.equals(oldUnit.getEmail(), newUnit.getEmail())) {
//            fieldNames.add("email");
//            originalValues.add(String.valueOf(oldUnit.getEmail()));
//            changedValues.add(String.valueOf(newUnit.getEmail()));
//        }
//
//        if (!Objects.equals(oldUnit.getPhoneNumber(), newUnit.getPhoneNumber())) {
//            fieldNames.add("phoneNumber");
//            originalValues.add(String.valueOf(oldUnit.getPhoneNumber()));
//            changedValues.add(String.valueOf(newUnit.getPhoneNumber()));
//        }
//
//        if (!Objects.equals(oldUnit.getRemark(), newUnit.getRemark())) {
//            fieldNames.add("remark");
//            originalValues.add(String.valueOf(oldUnit.getRemark()));
//            changedValues.add(String.valueOf(newUnit.getRemark()));
//        }
//
//        if (fieldNames.isEmpty()) {
//            return null;
//        }
//        log.setOriginalFields(String.join(", ", originalValues));
//        log.setUpdatedFields(String.join(", ", changedValues));

        repo.save(log);
    }

    @Override
    public void logDeleted(UnitHead deletedUnitHead) {
        UnitHeadLog log = new UnitHeadLog();
        log.setUnitCode(deletedUnitHead.getUnit().getUnitCode());
        log.setUnitName(deletedUnitHead.getUnit().getUnitName());
        log.setUnitHeadFullName(deletedUnitHead.getUnitHeadFullName());
        log.setVmyCode(deletedUnitHead.getVmyCode());
        log.setEmail(deletedUnitHead.getEmail());
        log.setPhoneNumber(deletedUnitHead.getPhoneNumber());
        /* createdBy */
        log.setRemark((deletedUnitHead.getRemark()));

//        log.setOriginalFields("Branch " + deletedUnit.getBranchManagerName() + " deleted.");
//        log.setUpdatedFields("Branch " + deletedUnit.getBranchManagerName() + " deleted.");

        repo.save(log);
    }

    @Override
    public List<UnitHeadLog> getAllLogs() {
        return repo.findAll();
    }

    @Override
    public List<UnitHeadLog> getLogsByUnitName(String unitName) {
        return repo.findByUnitName(unitName);
    }

    @Override
    public List<UnitHeadLog> getLogsForOneDay(String dateString) {

        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return repo.findByLastUpdatedAtBetween(start, end);
    }

    @Override
    public List<UnitHeadLog> getLogsForCustomDays(String startDateString, String endDateString) {

        LocalDate startDate = LocalDate.parse(startDateString, DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(endDateString, DATE_FORMATTER);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        return repo.findByLastUpdatedAtBetween(start, end);
    }

}
