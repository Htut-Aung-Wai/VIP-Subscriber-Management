package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.common.common.response.Basic;
import com.mytel.vip_subscriber_management.database.dto.SubscriberSearchDto;
import com.mytel.vip_subscriber_management.database.dto.VipSubscriberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VipSubscriberService {

    ResponseEntity<Basic> createVipSubscriber(VipSubscriberRequest vipSubscriberRequest);

    ResponseEntity<Basic> updateVipSubscriber(String vipSubscriberId, VipSubscriberRequest vipSubscriberRequest);

    ResponseEntity<Basic> getVipSubscriber(String vipSubscriberId);

    ResponseEntity<Basic> searchVipSubscribers(SubscriberSearchDto subscriberSearchDto, int page, int size);

    ResponseEntity<Basic> deleteVipSubscriber(String vipSubscriberId);

    ResponseEntity<?> importData(MultipartFile file);

    ResponseEntity<byte[]> downloadTemplate() throws IOException;

    ResponseEntity<?> exportData(SubscriberSearchDto subscriberSearchDto, int page, int size);
}
