package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
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
    public ResponseEntity<?> createUnit(@RequestBody Branch branch) {
        Branch created = service.createUnit(branch);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                created,
                "201",
                "Unit Creation Success.");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUnit() {
        List<Branch> allBranch = service.getAllUnit();

        return factory.buildSuccess(
                HttpStatus.OK,
                allBranch,
                "200",
                "All Unit Retrieved.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable("id") Long id) {
        Branch branch = service.getUnitById(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                branch,
                "200",
                "Id " + id + "Unit Retrieved.");
    }

    @GetMapping("/unit-head-full-name/{unitHeadFullName}")
    public ResponseEntity<?> getUnitByUnitHeadFullName(@PathVariable("unitHeadFullName") String unitHeadFullName) {
        List<Branch> branch = service.getUnitByUnitHeadFullName(unitHeadFullName);

        return factory.buildSuccess(
                HttpStatus.OK,
                branch,
                "200",
                unitHeadFullName + " Unit Retrieved.");
    }

    @PutMapping("/update/{unitCode}")
    public ResponseEntity<?> updateUnitByUnitCode(@PathVariable("unitCode") String unitCode, @RequestBody Branch branch) {
        Branch updated = service.updateUnitByUnitCode(unitCode, branch);

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
