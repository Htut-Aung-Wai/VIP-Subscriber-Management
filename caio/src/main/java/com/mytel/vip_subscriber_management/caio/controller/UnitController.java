package com.mytel.vip_subscriber_management.caio.controller;

import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.database.entity.Unit;
import com.mytel.vip_subscriber_management.service.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caio/unit")
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
                " Unit Creation Success."
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUnits(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {

        Page<Unit> allUnits = service.getAllUnit(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("units", allUnits.getContent());
        response.put("currentPage", allUnits.getNumber());
        response.put("totalItems", allUnits.getTotalElements());
        response.put("totalPages", allUnits.getTotalPages());

        return factory.buildSuccess(
                HttpStatus.OK,
                response,
                "200",
                "All Units Retrieved."
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable("id") Long id) {
        Unit unitById = service.getUnitById(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitById,
                "200",
                "Unit Id " + id + " Retrieved."
        );
    }

    @GetMapping("/unit-name/{unitName}")
    public ResponseEntity<?> getUnitByUnitName(@PathVariable("unitName") String unitName) {
        Unit unitByUnitName = service.getUnitByUnitName(unitName);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitByUnitName,
                "200",
                unitName + " Retrieved."
        );
    }

    @GetMapping("/unit-code/{unitCode}")
    public ResponseEntity<?> getUnitByUnitCode(@PathVariable("unitCode") String unitCode) {
        Unit unitByUnitCode = service.getUnitByUnitCode(unitCode);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitByUnitCode,
                "200",
                unitCode + " Retrieved."
        );
    }

    @PutMapping("/update/{unitCode}")
    public ResponseEntity<?> updateUnitByUnitCode(@PathVariable("unitCode") String unitCode, @RequestBody Unit unit) {
        Unit updated = service.updateUnitByUnitCode(unitCode, unit);

        return factory.buildSuccess(
                HttpStatus.OK,
                updated,
                "200",
                unitCode + " Updated. Unit Code Is Uneditable!"
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable("id") Long id) {
        service.deleteUnit(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                null,
                "200",
                "Unit Id " + id + " Deleted."
        );
    }
}
