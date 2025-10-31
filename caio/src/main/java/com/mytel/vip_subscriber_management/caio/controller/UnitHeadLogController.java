package com.mytel.vip_subscriber_management.caio.controller;

import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.database.entity.UnitHeadLog;
import com.mytel.vip_subscriber_management.service.service.UnitHeadLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caio/unit-head-log")
public class UnitHeadLogController {

    private final UnitHeadLogService service;
    private final ResponseFactory factory;

    @GetMapping("/all")
    public ResponseEntity<?> getAllLogs() {
        List<UnitHeadLog> logs = service.getAllLogs();

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                "All Unit Head Logs Retrieved.");
    }

    @GetMapping("/{unitName}")
    public ResponseEntity<?> getLogsByUnitName(@PathVariable String unitName) {
        List<UnitHeadLog> logs = service.getLogsByUnitName(unitName);

        return factory.buildSuccess(
                HttpStatus.OK,
                logs,
                "200",
                unitName + "'s Unit Head Logs Retrieved.");
    }

    //    /one-day?date=2000-02-20
    @GetMapping("/one-day")
    public ResponseEntity<?> getLogsForOneDay(@RequestParam("date") String date) {
        List<UnitHeadLog> logsForOneDay = service.getLogsForOneDay(date);

        return factory.buildSuccess(
                HttpStatus.OK,
                logsForOneDay,
                "200",
                " Unit Head Logs For " + date + " Retrieved.");
    }

    //    /custom-days?start=2000-02-20&end=2000-03-02
    @GetMapping("/custom-days")
    public ResponseEntity<?> getLogsForCustomDay(@RequestParam("start") String start, @RequestParam("end") String end) {
        List<UnitHeadLog> logsForCustomDays = service.getLogsForCustomDays(start, end);

        return factory.buildSuccess(
                HttpStatus.OK,
                logsForCustomDays,
                "200",
                " Unit Head Logs From " + start + " to " + end + " Retrieved.");
    }
}
