package com.mytel.vip_subscriber_management.confirm_page.controller;

import com.mytel.vip_subscriber_management.common.common.response.Basic;
import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.common.constant.ErrorCode;
import com.mytel.vip_subscriber_management.database.dto.request.LoginConfirmRequest;
import com.mytel.vip_subscriber_management.database.dto.request.LoginRequest;
import com.mytel.vip_subscriber_management.database.dto.response.LoginConfirmResponse;
import com.mytel.vip_subscriber_management.database.dto.response.LoginResponse;
import com.mytel.vip_subscriber_management.service.service.ConfirmPageUserService;
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
