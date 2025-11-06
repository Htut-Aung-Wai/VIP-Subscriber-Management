package com.mytel.vip_subscriber_management.caio.controller;

import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadResponseDto;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadUpdateDto;
import com.mytel.vip_subscriber_management.database.entity.UnitHead;
import com.mytel.vip_subscriber_management.service.service.UnitHeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Page<UnitHead> unitHead = service.getAll(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("units", unitHead.getContent());
        response.put("currentPage", unitHead.getNumber());
        response.put("totalItems", unitHead.getTotalElements());
        response.put("totalPages", unitHead.getTotalPages());

        return factory.buildSuccess(
                HttpStatus.OK,
                response,
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
        UnitHead unitHead = service.findByUnitCodeOrUnitName(keyword);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Unit Head " + keyword + " Retrieved."
        );
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<?> getByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        UnitHead unitHead = service.findByPhoneNumber(phoneNumber);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Unit Head With " + phoneNumber + " Retrieved."
        );
    }

    @GetMapping("/vmy-code/{vmyCode}")
    public ResponseEntity<?> getByVmyCode(@PathVariable("vmyCode") String vmyCode) {
        UnitHead unitHead = service.findByVmyCode(vmyCode);

        return factory.buildSuccess(
                HttpStatus.OK,
                unitHead,
                "200",
                "Unit Head With " + vmyCode + " Retrieved."
        );
    }

    //    /one-day?date=2000-02-20                                 for From Date
    @GetMapping("/one-day")
    public ResponseEntity<?> getCreatedAtForOneDay(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam("date") String date) {
        Page<UnitHead> fromDate = service.findByCreatedAtFromDate(date, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("units", fromDate.getContent());
        response.put("currentPage", fromDate.getNumber());
        response.put("totalItems", fromDate.getTotalElements());
        response.put("totalPages", fromDate.getTotalPages());

        return factory.buildSuccess(
                HttpStatus.OK,
                response,
                "200",
                " Unit Head Created At " + date + " Retrieved.");
    }

    //    /custom-days?start=2000-02-20&end=2000-03-02             for From Date To Date
    @GetMapping("/custom-days")
    public ResponseEntity<?> getCreatedAtForCustomDay(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam("from") String from, @RequestParam("to") String to) {
        Page<UnitHead> fromDateToDate = service.findByCreatedAtFromDateToDate(from, to, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("units", fromDateToDate.getContent());
        response.put("currentPage", fromDateToDate.getNumber());
        response.put("totalItems", fromDateToDate.getTotalElements());
        response.put("totalPages", fromDateToDate.getTotalPages());

        return factory.buildSuccess(
                HttpStatus.OK,
                response,
                "200",
                " Unit Head Creation Dates From " + from + " to " + to + " Retrieved.");
    }
}
