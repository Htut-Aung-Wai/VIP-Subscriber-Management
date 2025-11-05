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

    @GetMapping("/unit-code-or-name/{keyword}")
    public ResponseEntity<?> getByUnitCodeOrUnitName(@PathVariable("keyword") String keyword) {
        List<UnitHead> unitHead = service.findByUnitCodeOrUnitName(keyword);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Unit Head " + keyword + " Retrieved."
        );
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<?> getByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        List<UnitHead> unitHead = service.findByPhoneNumber(phoneNumber);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Unit Head With " + phoneNumber + " Retrieved."
        );
    }

    @GetMapping("/vmy-code/{vmyCode}")
    public ResponseEntity<?> getByVmyCode(@PathVariable("vmyCode") String vmyCode) {
        List<UnitHead> unitHead = service.findByVmyCode(vmyCode);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Unit Head With " + vmyCode + " Retrieved."
        );
    }

    //    /one-day?date=2000-02-20                                 for From Date
    @GetMapping("/one-day")
    public ResponseEntity<?> getCreatedAtForOneDay(@RequestParam("date") String date) {
        List<UnitHead> fromDate = service.findByCreatedAtFromDate(date);

        return factory.buildSuccess(
                HttpStatus.OK,
                fromDate,
                "200",
                " Unit Head Created At " + date + " Retrieved.");
    }

    //    /custom-days?start=2000-02-20&end=2000-03-02             for From Date To Date
    @GetMapping("/custom-days")
    public ResponseEntity<?> getCreatedAtForCustomDay(@RequestParam("from") String from, @RequestParam("to") String to) {
        List<UnitHead> fromDateToDate = service.findByCreatedAtFromDateToDate(from, to);

        return factory.buildSuccess(
                HttpStatus.OK,
                fromDateToDate,
                "200",
                " Unit Head Creation Dates From " + from + " to " + to + " Retrieved.");
    }
}
