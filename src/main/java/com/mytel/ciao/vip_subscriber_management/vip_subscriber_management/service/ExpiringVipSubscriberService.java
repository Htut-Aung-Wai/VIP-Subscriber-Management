package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriber;

import java.util.List;

public interface ExpiringVipSubscriberService {

    List<VipSubscriber> getExpiringSubscriberFilteredByBranchName(String branch);

}
