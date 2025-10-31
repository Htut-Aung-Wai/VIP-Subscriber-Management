package com.mytel.vip_subscriber_management.caio.controller;


import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.database.entity.Branch;
import com.mytel.vip_subscriber_management.service.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branch")
public class BranchController {

    private final BranchService service;
    private final ResponseFactory factory;

    @PostMapping("/create")
    public ResponseEntity<?> createBranch(@RequestBody Branch branch) {
        Branch created = service.createBranch(branch);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                created,
                "201",
                "Branch Creation Success.");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBranch() {
        List<Branch> allBranch = service.getAllBranch();

        return factory.buildSuccess(
                HttpStatus.OK,
                allBranch,
                "200",
                "All Branches Retrieved.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable("id") Long id) {
        Branch branch = service.getBranchById(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                branch,
                "200",
                "Id " + id + " Branch Retrieved.");
    }

    @GetMapping("/branch-manager-name/{branchManagerName}")
    public ResponseEntity<?> getBranchByBranchManagerName(@PathVariable("branchManagerName") String branchManagerName) {
        List<Branch> branch = service.getBranchByBranchManagerName(branchManagerName);

        return factory.buildSuccess(
                HttpStatus.OK,
                branch,
                "200",
                branchManagerName + "'s Branch Retrieved.");
    }

    @PutMapping("/update/{branchCode}")
    public ResponseEntity<?> updateBranchByBranchCode(@PathVariable("branchCode") String branchCode, @RequestBody Branch branch) {
        Branch updated = service.updateBranchByBranchCode(branchCode, branch);

        return factory.buildSuccess(
                HttpStatus.OK,
                updated,
                "200",
                branchCode + " Branch Updated. Branch Code Is Read-Only!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable("id") Long id) {
        service.deleteBranch(id);

        return factory.buildSuccess(
                HttpStatus.OK,
                null,
                "200",
                "Branch Deleted.");
    }
}
