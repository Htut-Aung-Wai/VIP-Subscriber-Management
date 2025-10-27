package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.service.impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.entity.RenewByManager;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.entity.RenewByManagerLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.repo.RenewalLogByManagerRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.service.RenewalLogByManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RenewalLogByManagerServiceImpl implements RenewalLogByManagerService {

    private final RenewalLogByManagerRepo repo;

    @Override
    public void logCreated(RenewByManager renewByManager) {
        RenewByManagerLog log = new RenewByManagerLog();
        log.setSubscriberNo(renewByManager.getSubscriberNo());
        log.setDecision(renewByManager.getDecision().toString());
        log.setConfirmedAt(renewByManager.getConfirmedAt());
        log.setBranchManagerName(renewByManager.getSubscriber().getBranch().getBranchManagerName());
        log.setBranchName(renewByManager.getSubscriber().getBranchName());
        repo.save(log);
    }

    @Override
    public List<RenewByManagerLog> getAllLogs() {
        return repo.findAll();
    }
}
