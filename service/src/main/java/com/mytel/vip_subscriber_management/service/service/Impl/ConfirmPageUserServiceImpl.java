package com.mytel.vip_subscriber_management.service.service.Impl;


import com.mytel.vip_subscriber_management.common.common.utils.JwtUtils;
import com.mytel.vip_subscriber_management.common.constant.ErrorCode;
import com.mytel.vip_subscriber_management.common.exception.CommonException;
import com.mytel.vip_subscriber_management.database.dto.request.LoginConfirmRequest;
import com.mytel.vip_subscriber_management.database.dto.request.LoginRequest;
import com.mytel.vip_subscriber_management.database.dto.response.LoginConfirmResponse;
import com.mytel.vip_subscriber_management.database.dto.response.LoginResponse;
import com.mytel.vip_subscriber_management.database.entity.ConfirmPageUser;
import com.mytel.vip_subscriber_management.database.entity.Otp;
import com.mytel.vip_subscriber_management.database.repository.ConfirmPageUserRepo;
import com.mytel.vip_subscriber_management.database.repository.OtpRepo;
import com.mytel.vip_subscriber_management.service.service.ConfirmPageUserService;
import com.mytel.vip_subscriber_management.service.service.OtpService;
import com.mytel.vip_subscriber_management.service.service.SendSMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ConfirmPageUserServiceImpl implements ConfirmPageUserService {

    private final ConfirmPageUserRepo confirmPageUserRepo;
    private final OtpRepo otpRepo;
    private final OtpService otpService;
    private final JwtUtils jwtUtil;
    private final SendSMSService sendSMSService;

    private static final int MAX_REQUESTS_PER_MINUTE = 5;

    @Override
    public LoginResponse validateIsdnAndSendOTP(LoginRequest request) {

        String formattedIsdn = request.getIsdn();

        Optional<ConfirmPageUser> user = confirmPageUserRepo.findByIsdn(formattedIsdn);
        if (user.isPresent()) {
            // Check OTP request limit
            if (hasExceededRequestLimit(formattedIsdn)) {
                throw new CommonException(ErrorCode.OTP_EXCEED_LIMIT);
            }
            Otp otp = otpService.generateOtp(formattedIsdn);

            LoginResponse response = new LoginResponse();
            response.setOtpId(otp.getId());
            response.setExpireAt(otp.getExpirationTime());
            return response;
        } else {
            throw new CommonException(ErrorCode.INVALID_NUMBER);
        }
    }

    @Override
    public LoginResponse resendOTP(LoginRequest request) {
        return validateIsdnAndSendOTP(request);
    }

    @Override
    public LoginConfirmResponse validateOTP(LoginConfirmRequest request) {
        String formattedIsdn = request.getIsdn();
        Otp otpResponse = otpService.validateOtp(request.getOtp(), formattedIsdn);
        if ( otpResponse == null) {
            throw new CommonException(ErrorCode.OTP_WRONG);
        }

        String jwtToken = jwtUtil.generateToken(formattedIsdn);
        LoginConfirmResponse response = new LoginConfirmResponse();
        response.setAccessToken(jwtToken);
        response.setExpiredAt(otpResponse.getExpirationTime());
        return response;

    }

    private boolean hasExceededRequestLimit(String isdn) {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        List<Otp> recentRequests = otpRepo.findByIsdnAndRequestTimeAfter(isdn, oneMinuteAgo);

        return recentRequests.size() >= MAX_REQUESTS_PER_MINUTE;
    }
}
