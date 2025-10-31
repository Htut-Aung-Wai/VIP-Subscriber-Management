package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.entity.VipSubscriber;

import java.util.List;

public interface ExpiringVipSubscriberService {

    List<VipSubscriber> getExpiringSubscriberFilteredByBranchName(String branchName);

}
