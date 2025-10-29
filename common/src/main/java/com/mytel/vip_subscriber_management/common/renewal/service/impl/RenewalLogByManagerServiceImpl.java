package com.mytel.vip_subscriber_management.common.renewal.service.impl;

import com.example.vip_management.renewal.entity.RenewByManager;
import com.example.vip_management.renewal.entity.RenewByManagerLog;
import com.example.vip_management.renewal.repo.RenewalLogByManagerRepo;
import com.example.vip_management.renewal.service.RenewalLogByManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RenewalLogByManagerServiceImpl implements RenewalLogByManagerService {

    private final RenewalLogByManagerRepo repo;

    @Override
    @Transactional
    public void logCreated(RenewByManager renewByManager) {
        RenewByManagerLog log = new RenewByManagerLog();
        log.setSubscriberNo(renewByManager.getSubscriberNo());
        log.setDecision(renewByManager.getDecision().toString());
        log.setConfirmedAt(renewByManager.getConfirmedAt());
        log.setBranchManagerName(renewByManager.getSubscriber().getUnit().getBranchManagerName());
        log.setBranchName(renewByManager.getSubscriber().getBranchName());
        repo.save(log);
    }

    @Override
    public List<RenewByManagerLog> getAllLogs() {
        return repo.findAll();
    }
}
