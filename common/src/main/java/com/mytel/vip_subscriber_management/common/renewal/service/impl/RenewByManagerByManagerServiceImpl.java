package com.mytel.vip_subscriber_management.common.renewal.service.impl;

import com.example.vip_management.entity.VipSubscriber;
import com.example.vip_management.exception.CommonException;
import com.example.vip_management.renewal.dto.RenewalDto;
import com.example.vip_management.renewal.entity.RenewByManager;
import com.example.vip_management.renewal.repo.RenewByManagerRepo;
import com.example.vip_management.renewal.service.RenewByManagerService;
import com.example.vip_management.renewal.service.RenewalLogByManagerService;
import com.example.vip_management.repo.VipSubscriberRepo;
import com.example.vip_management.service.ExpiringVipSubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RenewByManagerByManagerServiceImpl implements RenewByManagerService {

    private final RenewByManagerRepo renewRepo;
    private final VipSubscriberRepo vipRepo;
    private final ExpiringVipSubscriberService expiringVipSubscriberService;
    private final RenewalLogByManagerService service;

    @Override
    @Transactional
    public List<RenewByManager> renewByManager(RenewByManager renewByManager, String branch) {
        List<VipSubscriber> subscribers = expiringVipSubscriberService.getExpiringSubscriberFilteredByBranchName(branch);
        List<RenewByManager> renewByManagers = new ArrayList<>();

        for (VipSubscriber sub : subscribers) {
            RenewByManager renewRecord = new RenewByManager();
            renewRecord.setDecision(renewByManager.getDecision());
            renewRecord.setSubscriber(sub);
            renewRecord.setSubscriberNo(sub.getSubscriberNo());
            renewRecord.setBranchName(sub.getBranchName());
            RenewByManager saved = renewRepo.save(renewRecord);

            service.logCreated(saved);

            renewByManagers.add(saved);
        }

        return renewByManagers;
    }

    @Override
    public List<RenewByManager> partialRenewByManager(RenewalDto dto) {
        List<RenewByManager> renewByManagers = new ArrayList<>();
        for (RenewalDto.RenewItem item : dto.getDecisions()) {
            VipSubscriber subscriber = vipRepo.findById(item.getSubscriberId())
                    .orElseThrow(() -> new CommonException("ERR_404", "Subscriber ID " + item.getSubscriberId() + " Not Found"));

            RenewByManager record = new RenewByManager();
            record.setSubscriber(subscriber);
            record.setSubscriberNo(subscriber.getSubscriberNo());
            record.setBranchName(subscriber.getBranchName());
            record.setDecision(item.getDecision());
            RenewByManager saved = renewRepo.save(record);

            service.logCreated(saved);

            renewByManagers.add(saved);
        }

        return renewByManagers;
    }
}
