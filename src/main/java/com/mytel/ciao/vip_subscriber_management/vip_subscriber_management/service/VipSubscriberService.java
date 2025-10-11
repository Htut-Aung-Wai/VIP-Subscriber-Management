package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.Basic;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.VipSubscriberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VipSubscriberService {

    ResponseEntity<Basic> createVipSubscriber(VipSubscriberRequest vipSubscriberRequest);

    ResponseEntity<Basic> updateVipSubscriber(String vipSubscriberId,VipSubscriberRequest vipSubscriberRequest);

    ResponseEntity<Basic> getVipSubscriber(String vipSubscriberId);

    ResponseEntity<Basic> getAllVipSubscribers();

    ResponseEntity<Basic> deleteVipSubscriber(String vipSubscriberId);

    ResponseEntity<Basic> importData(MultipartFile file);

    ResponseEntity<byte[]> downloadTemplate() throws IOException;

    ResponseEntity<?> exportData();
}
