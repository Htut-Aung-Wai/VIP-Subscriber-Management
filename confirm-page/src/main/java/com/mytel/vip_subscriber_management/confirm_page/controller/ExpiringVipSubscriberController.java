package com.mytel.vip_subscriber_management.confirm_page.controller;

import com.mytel.vip_subscriber_management.common.common.response.Basic;
import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.common.constant.ErrorCode;
import com.mytel.vip_subscriber_management.database.entity.VipSubscriber;
import com.mytel.vip_subscriber_management.service.excel.ExcelExport;
import com.mytel.vip_subscriber_management.service.service.ExpiringVipSubscriberService;
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
@RequestMapping("/confirm-page/expire-subscribers")
public class ExpiringVipSubscriberController {

    private final ExpiringVipSubscriberService service;
    private final ResponseFactory factory;

    /**
     * For Confirm Page
     */
    @GetMapping("/{branchName}")
    public ResponseEntity<?> getExpiringSubscribersByUnitName(@PathVariable("branchName") String branchName) {
        List<VipSubscriber> subscribers = service.getExpiringSubscriberFilteredByBranchName(branchName);

        return factory.buildSuccess(
                HttpStatus.OK,
                subscribers,
                "200",
                "Expiring Subscribers at next 2-Month.");
    }

    @GetMapping("/export/{branchName}")
    public ResponseEntity<Resource> exportExpiringSubscribersXlsx(@PathVariable("branchName") String branchName) throws IOException {
        List<VipSubscriber> expiring = service.getExpiringSubscriberFilteredByBranchName(branchName);
        ByteArrayInputStream in = ExcelExport.exportToExcel(expiring);

        InputStreamResource file = new InputStreamResource(in);
        log.info("Expiring count for {} => {}", branchName, expiring.size());
        expiring.forEach(s -> log.info("expiring: {} -> {}", s.getSubscriberNo(), s.getExpiryDate()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expiring_subscribers.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }

    @GetMapping("/test")
    public ResponseEntity<Basic> testSecurity() {

        return factory.buildSuccess(
                HttpStatus.OK,
                "Security test succeed",
                ErrorCode.SUCCESS,
                "[Succeed]"
        );
    }
}
