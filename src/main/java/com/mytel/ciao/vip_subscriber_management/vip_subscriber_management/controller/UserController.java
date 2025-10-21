package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.UserDto;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.User;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch-manager")
public class UserController {

    private final UserService service;
    private final ResponseFactory factory;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User user) {
        User user1 = service.create(user);

        return factory.buildSuccess(
                HttpStatus.CREATED,
                user1,
                "201",
                "Branch Manager Created."
        );
    }

    @PostMapping("/create-all")
    public ResponseEntity<List<?>> createAll(@RequestBody List<User> users) {
        List<User> userList = service.createAll(users);

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/vmy-code/{vmyCode}")
    public ResponseEntity<?> getByVmyCode(@PathVariable("vmyCode") String vmyCode) {
        User user = service.getByVmyCode(vmyCode);

        return factory.buildSuccess(
                HttpStatus.OK,
                user,
                "200",
                vmyCode + " Branch Manager Retrieved."
        );
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<?> getByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        User user = service.getByPhoneNumber(phoneNumber);

        return factory.buildSuccess(
                HttpStatus.OK,
                user,
                "200",
                phoneNumber + " Branch Manager Retrieved."
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<User> users = service.getAll();

        return factory.buildSuccess(
                HttpStatus.OK,
                users,
                "200",
                "All Branch Manager Retrieved."
        );
    }

    @PutMapping("/update/{vmyCode}")
    public ResponseEntity<?> update(@PathVariable("vmyCode") String vmyCode, @RequestBody UserDto user) {
        User user1 = service.update(vmyCode, user);

        return factory.buildSuccess(
                HttpStatus.OK,
                user1,
                "200",
                "Branch Manager Updated. VMY CODE Is Un-Editable."
        );
    }

    @DeleteMapping("/delete/{vmyCode}")
    public ResponseEntity<?> delete(@PathVariable("vmyCode") String vmyCode) {
        service.delete(vmyCode);

        return factory.buildSuccess(
                HttpStatus.OK,
                null,
                "200",
                vmyCode + " Branch Manager Deleted."
        );
    }
}
