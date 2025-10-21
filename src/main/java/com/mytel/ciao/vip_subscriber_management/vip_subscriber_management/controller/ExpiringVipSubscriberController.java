package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.excel.ExcelExport;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriber;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.ExpiringVipSubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expire-subscribers")
public class ExpiringVipSubscriberController {

    private final ExpiringVipSubscriberService service;
    private final ResponseFactory factory;

    /**
     * For Landing Page
     */
    @GetMapping("/{branch}")
    public ResponseEntity<?> getExpiringSubscribersByBranchName(@PathVariable("branch") String branch) {
        List<VipSubscriber> subscribers = service.getExpiringSubscriberFilteredByBranchName(branch);

        return factory.buildSuccess(
                HttpStatus.OK,
                subscribers,
                "200",
                "Expiring Subscribers at next 2-Month.");
    }

    @GetMapping("/export-excel/{branch}")
    public ResponseEntity<Resource> exportExpiringSubscribersXlsx(@PathVariable("branch") String branch) throws IOException {
        List<VipSubscriber> expiring = service.getExpiringSubscriberFilteredByBranchName(branch);
        ByteArrayInputStream in = ExcelExport.exportToExcel(expiring);

        InputStreamResource file = new InputStreamResource(in);
        log.info("Expiring count for {} => {}", branch, expiring.size());
        expiring.forEach(s -> log.info("expiring: {} -> {}", s.getSubscriberNo(), s.getExpiryDate()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expiring_subscribers.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }

}
