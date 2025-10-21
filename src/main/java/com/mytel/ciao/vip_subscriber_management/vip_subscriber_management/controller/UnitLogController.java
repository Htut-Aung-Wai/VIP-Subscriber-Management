package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitLogService;
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
@RequestMapping("/api/unit-log")
public class UnitLogController {

    private final UnitLogService service;
    private final ResponseFactory factory;

    @GetMapping("/all")
    public ResponseEntity<?> getAllLogs() {
        List<UnitLog> logs = service.getAllLogs();

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                "All Unit Logs Retrieved.");
    }

    @GetMapping("/{unitName}")
    public ResponseEntity<?> getLogsByUnitName(@PathVariable String unitName) {
        List<UnitLog> logs = service.getLogsByUnitName(unitName);

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                unitName + " Unit Logs Retrieved.");
    }

    @GetMapping("/{unitName}/{actionType}")
    public ResponseEntity<?> getLogsByUnitNameAndAction(
            @PathVariable String unitName,
            @PathVariable UnitLog.ActionType actionType) {
        List<UnitLog> logs = service.getLogsByUnitNameAndAction(unitName, actionType);

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                unitName + " Unit Logs Retrieved.");
    }
}
