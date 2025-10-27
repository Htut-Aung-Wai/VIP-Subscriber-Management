package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.BranchLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.BranchLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caio/unit-log")
public class BranchLogController {

    private final BranchLogService service;
    private final ResponseFactory factory;

    @GetMapping("/all")
    public ResponseEntity<?> getAllLogs() {
        List<BranchLog> logs = service.getAllLogs();

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                "All Branches Logs Retrieved.");
    }

    @GetMapping("/{branchName}")
    public ResponseEntity<?> getLogsByBranchName(@PathVariable String branchName) {
        List<BranchLog> logs = service.getLogsByBranchName(branchName);

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                branchName + " Branch Logs Retrieved.");
    }

    @GetMapping("/{branchName}/{actionType}")
    public ResponseEntity<?> getLogsByBranchNameAndAction(
            @PathVariable String branchName,
            @PathVariable String actionType) {
        List<BranchLog> logs = service.getLogsByBranchNameAndAction(branchName, actionType);

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                branchName + " Branch Logs Retrieved.");
    }
}
