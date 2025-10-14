package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.constant.VipSubscriberLogActionType;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriber;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriberLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.VipSubscriberLogRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.VipSubscriberRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.VipSubscriberLogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VipSubscriberLogServiceImpl implements VipSubscriberLogService {

    private final VipSubscriberLogRepo vipSubscriberLogRepo;
    private final VipSubscriberRepo vipSubscriberRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public VipSubscriberLogServiceImpl(VipSubscriberLogRepo vipSubscriberLogRepo, VipSubscriberRepo vipSubscriberRepo) {
        this.vipSubscriberLogRepo = vipSubscriberLogRepo;
        this.vipSubscriberRepo = vipSubscriberRepo;
    }


    @Override
    public void createLog(VipSubscriber vipSubscriber) throws JsonProcessingException {

        VipSubscriberLog vipSubscriberLog=new VipSubscriberLog();
        vipSubscriberLog.setSubscriberId(vipSubscriber.getId());
        vipSubscriberLog.setSubscriberNo(vipSubscriber.getSubscriberNo());
        vipSubscriberLog.setActionType(VipSubscriberLogActionType.CREATE.name());
        vipSubscriberLog.setActionUsername(getCurrentLoginUser());
        vipSubscriberLog.setNewData(objectMapper.writeValueAsString(vipSubscriber));
        vipSubscriberLog.setIsSuccess(true);
        vipSubscriberLogRepo.save(vipSubscriberLog);

    }


    @Override
    public void updateLog(VipSubscriber vipSubscriber) throws JsonProcessingException {

        VipSubscriber vipSubscriberOld = vipSubscriberRepo.findById(vipSubscriber.getId()).orElse(null);
        VipSubscriberLog vipSubscriberLog=new VipSubscriberLog();
        vipSubscriberLog.setSubscriberId(vipSubscriber.getId());
        vipSubscriberLog.setSubscriberNo(vipSubscriber.getSubscriberNo());
        vipSubscriberLog.setActionType(VipSubscriberLogActionType.UPDATE.name());
        vipSubscriberLog.setActionUsername(getCurrentLoginUser());
        ObjectMapper objectMapper = new ObjectMapper();
        vipSubscriberLog.setOldData(
                vipSubscriberOld != null ? objectMapper.writeValueAsString(vipSubscriberOld) : "{}"
        );
        vipSubscriberLog.setNewData(objectMapper.writeValueAsString(vipSubscriber));
        vipSubscriberLog.setIsSuccess(true);
        vipSubscriberLogRepo.save(vipSubscriberLog);

    }

    @Override
    public void deleteLog(String subscriberId) throws JsonProcessingException {

        VipSubscriber vipSubscriberOld = vipSubscriberRepo.findById(subscriberId)
                .orElse(null);


        VipSubscriberLog vipSubscriberLog = new VipSubscriberLog();

        vipSubscriberLog.setSubscriberId(subscriberId);
        vipSubscriberLog.setActionType(VipSubscriberLogActionType.DELETE.name());
        vipSubscriberLog.setActionUsername(getCurrentLoginUser());
        if (vipSubscriberOld != null) {
            vipSubscriberLog.setOldData(objectMapper.writeValueAsString(vipSubscriberOld));
            vipSubscriberLog.setSubscriberNo(vipSubscriberOld.getSubscriberNo());
        } else {
            vipSubscriberLog.setOldData("{}");
        }
        vipSubscriberLog.setNewData("{}");
        vipSubscriberLog.setIsSuccess(true);
        vipSubscriberLogRepo.save(vipSubscriberLog);
    }

    @Override
    public void errorlog(String subscriberId,String subscriberNo,String actionType,String errorDescription)
    {
        VipSubscriberLog vipSubscriberLog=new VipSubscriberLog();

        vipSubscriberLog.setSubscriberId(subscriberId);
        vipSubscriberLog.setSubscriberNo(subscriberNo);
        vipSubscriberLog.setActionType(actionType);
        vipSubscriberLog.setIsSuccess(false);
        vipSubscriberLog.setDescription(errorDescription);
        vipSubscriberLog.setActionUsername(getCurrentLoginUser());
        vipSubscriberLogRepo.save(vipSubscriberLog);
    }

    private String getCurrentLoginUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
