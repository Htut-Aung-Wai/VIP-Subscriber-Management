package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriber;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.VipSubscriberRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.ExpiringVipSubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpiringVipSubscriberServiceImpl implements ExpiringVipSubscriberService {

    private final VipSubscriberRepo repo;

    @Override
    public List<VipSubscriber> getExpiringSubscriberFilteredByBranchName(String branchName) {
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        LocalDate futureStart = now.plusMonths(2).withDayOfMonth(1);
        LocalDate futureEnd = futureStart.withDayOfMonth(futureStart.lengthOfMonth());

        LocalDateTime start = futureStart.atStartOfDay();
        LocalDateTime end = futureEnd.atTime(LocalTime.MAX);

        return repo.findExpiringSubscribersByBranchName(start, end, branchName);

    }
}
