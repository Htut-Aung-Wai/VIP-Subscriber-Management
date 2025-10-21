package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Unit;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unit")
public class UnitController {

    private final UnitService service;
    private final ResponseFactory factory;

    @PostMapping("/create")
    public ResponseEntity<?> createUnit(@RequestBody Unit unit) {
        Unit created = service.createUnit(unit);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                created,
                "201",
                "Unit Creation Success.");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUnit() {
        List<Unit> allUnit = service.getAllUnit();

        return factory.buildSuccess(
                HttpStatus.OK,
                allUnit,
                "200",
                "All Unit Retrieved.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable("id") Long id) {
        Unit unit = service.getUnitById(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                unit,
                "200",
                "Id " + id + "Unit Retrieved.");
    }

    @GetMapping("/unit-full-name/{unitFullName}")
    public ResponseEntity<?> getUnitByUnitFullName(@PathVariable("unitFullName") String unitFullName) {
        List<Unit> unit = service.getUnitByUnitFullName(unitFullName);

        return factory.buildSuccess(
                HttpStatus.OK,
                unit,
                "200",
                unitFullName + " Unit Retrieved.");
    }

    @PutMapping("/update/{unitCode}")
    public ResponseEntity<?> updateUnitByUnitCode(@PathVariable("unitCode") String unitCode, @RequestBody Unit unit) {
        Unit updated = service.updateUnitByUnitCode(unitCode, unit);

        return factory.buildSuccess(
                HttpStatus.OK,
                updated,
                "200",
                unitCode + " Unit Updated. Unit Code Is Read-Only!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable("id") Long id) {
        service.deleteUnit(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                null,
                "200",
                "Unit Deleted.");
    }
}
