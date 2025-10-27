package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.dto.RenewalDto;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.entity.RenewByManager;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.renewal.service.RenewByManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/renew-by-manager")
public class RenewByManagerController {

    private final RenewByManagerService service;
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
