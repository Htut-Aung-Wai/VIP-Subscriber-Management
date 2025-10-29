package com.mytel.vip_subscriber_management.service.service.Impl;

import com.mytel.vip_subscriber_management.common.exception.CommonException;
import com.mytel.vip_subscriber_management.database.dto.RenewalDto;
import com.mytel.vip_subscriber_management.database.entity.RenewByManager;
import com.mytel.vip_subscriber_management.database.entity.VipSubscriber;
import com.mytel.vip_subscriber_management.database.repository.RenewByManagerRepo;
import com.mytel.vip_subscriber_management.database.repository.VipSubscriberRepo;
import com.mytel.vip_subscriber_management.service.service.ExpiringVipSubscriberService;
import com.mytel.vip_subscriber_management.service.service.RenewByManagerService;
import com.mytel.vip_subscriber_management.service.service.RenewalLogByManagerService;
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
