package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriber;

public interface VipSubscriberLogService {

    void createLog(VipSubscriber vipSubscriber) throws JsonProcessingException;
    void updateLog(VipSubscriber vipSubscriber) throws JsonProcessingException;
    void deleteLog(String subscriberId) throws JsonProcessingException;
    void errorlog(String subscriberId,String subscriberNo,String actionType,String errorDescription);
}
