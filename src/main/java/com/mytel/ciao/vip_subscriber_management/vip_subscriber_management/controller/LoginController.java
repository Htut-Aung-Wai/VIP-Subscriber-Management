package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.Basic;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.ResponseFactory;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.constant.ErrorCode;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.request.LoginConfirmRequest;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.request.LoginRequest;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.response.LoginConfirmResponse;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.response.LoginResponse;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.ConfirmPageUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/confirm-page/auth")
public class LoginController {

    private final ConfirmPageUserService confirmPageUserService;
    private final ResponseFactory responseFactory;

    @PostMapping("/request-otp-login")
    public ResponseEntity<Basic> login(@RequestBody LoginRequest request) {
        LoginResponse result = confirmPageUserService.validateIsdnAndSendOTP(request);
        return responseFactory.buildSuccess(
                HttpStatus.OK,
                result,
                ErrorCode.SUCCESS,
                "[Succeed] Validate Otp Success"
        );
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<Basic> resendOTP(@RequestBody LoginRequest request) {
       LoginResponse result = confirmPageUserService.resendOTP(request);
        return responseFactory.buildSuccess(
                HttpStatus.OK,
                result,
                ErrorCode.SUCCESS,
                "[Succeed] Resend Otp Success"
        );
    }

    @PostMapping("/verify-otp-login")
    public ResponseEntity<Basic> verifyOTP(@RequestBody LoginConfirmRequest verify) {
        LoginConfirmResponse result = confirmPageUserService.validateOTP(verify);
        return responseFactory.buildSuccess(
                HttpStatus.OK,
                result,
                ErrorCode.SUCCESS,
                "[Succeed] Verify Otp Success"
        );
    }
}
