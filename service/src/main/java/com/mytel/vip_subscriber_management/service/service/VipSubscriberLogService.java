package com.mytel.vip_subscriber_management.service.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mytel.vip_subscriber_management.database.entity.VipSubscriber;

public interface VipSubscriberLogService {

    void createLog(VipSubscriber vipSubscriber) throws JsonProcessingException;
    void updateLog(VipSubscriber vipSubscriber) throws JsonProcessingException;
    void deleteLog(String subscriberId) throws JsonProcessingException;
    void errorlog(String subscriberId,String subscriberNo,String actionType,String errorDescription);
}
