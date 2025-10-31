package com.mytel.vip_subscriber_management.caio.controller;

import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadResponseDto;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadUpdateDto;
import com.mytel.vip_subscriber_management.database.entity.UnitHead;
import com.mytel.vip_subscriber_management.service.service.UnitHeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caio/unit-head")
public class UnitHeadController {

    private final UnitHeadService service;
    private final ResponseFactory factory;

    @PostMapping("/create/{unitCode}")
    public ResponseEntity<?> create(@Valid @RequestBody UnitHead unitHead, @PathVariable("unitCode") String unitCode) {
        UnitHead created = service.create(unitHead, unitCode);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                created,
                "201",
                "Unit Head Creation Success."
        );
    }

    @GetMapping("/full-name/{unitHeadFullName}")
    public ResponseEntity<?> getByUnitHeadFullName(@PathVariable("unitHeadFullName") String unitHeadFullName) {
        UnitHeadResponseDto dto = service.getByUnitHeadFullName(unitHeadFullName);

        return factory.buildSuccess(
                HttpStatus.OK,
                dto,
                "200",
                "Unit Head " + dto.getUnitHeadFullName() + " Retrieved."
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        UnitHead unitHead = service.getById(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Id " + id + " Unit Head Retrieved."
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<UnitHead> unitHead = service.getAll();

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                " All Unit Heads Retrieved."
        );
    }

    @PutMapping("/update/{unitCode}")
    public ResponseEntity<?> updateByUnitCode(@RequestBody UnitHeadUpdateDto dto, @PathVariable("unitCode") String unitCode) {
        UnitHead unitHead = service.updateByUnitCode(dto, unitCode);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Unit Head " + unitHead.getUnitHeadFullName() + " Updated."
        );
    }

    @DeleteMapping("/delete/{unitCode}")
    public ResponseEntity<?> deleteByUnitCode(@PathVariable("unitCode") String unitCode) {
        service.deleteByUnitCode(unitCode);

        return factory.buildSuccess(
                HttpStatus.OK,
                null,
                "200",
                unitCode + " Unit Head Deleted."
        );
    }
}
