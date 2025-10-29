package com.mytel.vip_subscriber_management.confirm_page.controller;

import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.database.dto.RenewalDto;
import com.mytel.vip_subscriber_management.database.entity.RenewByManager;
import com.mytel.vip_subscriber_management.service.service.ExpiringVipSubscriberService;
import com.mytel.vip_subscriber_management.service.service.RenewByManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm-page/renew-by-manager")
public class RenewByManagerController {

    private final RenewByManagerService service;
    private final ExpiringVipSubscriberService expiringVipSubscriberService;
    private final ResponseFactory factory;

    @PutMapping("/renew/{branch}")
    public ResponseEntity<?> renewalProcess(@RequestBody RenewByManager renew, @PathVariable("branch") String branch) {
        List<RenewByManager> renewByManagers = service.renewByManager(renew, branch);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                renewByManagers,
                "201",
                "Renewal Process Success.");
    }

    @PutMapping("/partial-renew")
    public ResponseEntity<?> partialRenewalProcess(@RequestBody RenewalDto dto) {
        List<RenewByManager> renewByManagers = service.partialRenewByManager(dto);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                renewByManagers,
                "201",
                "Partial Renewal Process Success.");
    }

}
