package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipPackage;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.VipPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/packages")
public class VipPackagesController {

    private final VipPackageService service;
    private final ResponseFactory factory;

    @PostMapping("/create")
    public ResponseEntity<?> createPackages(@RequestBody VipPackage packages) {
        VipPackage created = service.createPackages(packages);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                created,
                "201",
                "VIP Package Creation Success.");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPackages() {
        List<VipPackage> packages = service.getAllPackages();

        return factory.buildSuccess(
                HttpStatus.OK,
                packages,
                "200",
                "All VIP Packages Retrieved.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePackages(@PathVariable("id") Long id, @RequestBody VipPackage packages) {
        VipPackage updated = service.updatePackages(id, packages);

        return factory.buildSuccess(
                HttpStatus.OK,
                updated,
                "200",
                "VIP Package Updated.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable("id") Long id) {
        service.deletePackages(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                null,
                "200",
                "VIP Package Deleted.");
    }
}
