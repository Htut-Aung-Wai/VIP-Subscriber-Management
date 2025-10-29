package com.mytel.vip_subscriber_management.common.renewal.controller;

import com.example.vip_management.common.response.ResponseFactory;
import com.example.vip_management.renewal.entity.RenewByManagerLog;
import com.example.vip_management.renewal.service.RenewalLogByManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/renew-log-by-manager")
public class RenewalLogByManagerController {

    private final RenewalLogByManagerService service;
    private final ResponseFactory factory;

    @GetMapping("/all")
    public ResponseEntity<?> getAllLogs() {
        List<RenewByManagerLog> logs = service.getAllLogs();

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                "All Renewal Logs Retrieved.");
    }
}
