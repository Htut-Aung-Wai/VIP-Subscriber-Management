package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHeadLog;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitHeadLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unit-log")
public class UnitHeadLogController {

    private final UnitHeadLogService service;

    @GetMapping("/all")
    public ResponseEntity<List<UnitHeadLog>> getAllLogs() {
        List<UnitHeadLog> logs = service.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{unitName}")
    public ResponseEntity<List<UnitHeadLog>> getLogsByUnitName(@PathVariable String unitName) {
        List<UnitHeadLog> logs = service.getLogsByUnitName(unitName);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{unitName}/{actionType}")
    public ResponseEntity<List<UnitHeadLog>> getLogsByUnitNameAndAction(
            @PathVariable String unitName,
            @PathVariable UnitHeadLog.ActionType actionType) {
        List<UnitHeadLog> logs = service.getLogsByUnitNameAndAction(unitName, actionType);
        return ResponseEntity.ok(logs);
    }
}
