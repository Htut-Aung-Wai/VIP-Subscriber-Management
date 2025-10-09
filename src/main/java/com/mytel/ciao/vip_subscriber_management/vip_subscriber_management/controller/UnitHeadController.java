package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHead;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitHeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unit-head")
public class UnitHeadController {

    private final UnitHeadService service;
    private final ResponseFactory factory;

    @PostMapping("/create")
    public ResponseEntity<?> createUnitHead(@RequestBody UnitHead unitHead) {
        UnitHead created = service.createUnitHead(unitHead);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                created,
                "201",
                "Unit Head Creation Success.");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUnitHead() {
        List<UnitHead> allUnitHeads = service.getAllUnitHead();

        return factory.buildSuccess(
                HttpStatus.OK,
                allUnitHeads,
                "200",
                "All Unit Head Retrieved.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUnitHeadById(@PathVariable("id") Long id) {
        UnitHead unitHead = service.getUnitHeadById(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Id " + id + "Unit Head Retrieved.");
    }

    @PutMapping("/update/{unitName}")
    public ResponseEntity<?> updateUnitHeadByName(@PathVariable("unitName") String unitName, @RequestBody UnitHead unitHead) {
        UnitHead updated = service.updateUnitHeadByUnitName(unitName, unitHead);

        return factory.buildSuccess(
                HttpStatus.OK,
                updated,
                "200",
                "Unit Head Updated.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUnitHead(@PathVariable("id") Long id) {
        service.deleteUnitHead(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                null,
                "200",
                "Unit Head Deleted.");
    }
}
