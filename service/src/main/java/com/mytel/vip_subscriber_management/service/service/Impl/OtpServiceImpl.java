package com.mytel.vip_subscriber_management.service.service.Impl;


import com.mytel.vip_subscriber_management.common.common.utils.MytelUtils;
import com.mytel.vip_subscriber_management.common.constant.ErrorCode;
import com.mytel.vip_subscriber_management.common.exception.CommonException;
import com.mytel.vip_subscriber_management.database.entity.Otp;
import com.mytel.vip_subscriber_management.database.repository.OtpRepo;
import com.mytel.vip_subscriber_management.service.service.OtpService;
import com.mytel.vip_subscriber_management.service.service.SendSMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class OtpServiceImpl implements OtpService {

    private final OtpRepo otpRepo;
    private static final int OTP_EXPIRATION_MINUTES = 60;
    private final SendSMSService sendSMSService;

    @Value("${is_dev_test_mode}")
    private String testMode;
    @Override
    // Generate OTP and save to database
    public Otp generateOtp(String isdn) {
        String otp = "";

        if(testMode.equals("true")){
             otp = "000000";
        } else {
            otp = generateRandomOtp();
        }

        Otp otpInfo = new Otp();
        otpInfo.setIsdn(isdn);
        otpInfo.setOtp(otp);
        otpInfo.setExpirationTime(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));
        otpInfo.setRequestTime(LocalDateTime.now());
        otpInfo.setUsed(false);
        Otp savedOtp = otpRepo.save(otpInfo);
        if(!savedOtp.getOtp().equals("000000")){
            sendOtpToUser(isdn, otp, otpInfo.getExpirationTime());
        }
        return savedOtp;
    }

    @Override
    public Otp validateOtp(String otp, String isdn) {
        if (testMode.equals("true")) {
            Otp testOtp = new Otp();
            testOtp.setOtp(otp);
            testOtp.setIsdn(isdn);
            testOtp.setUsed(false);
            testOtp.setExpirationTime(LocalDateTime.now().plusMinutes(5));
            return testOtp;
        } else {
            Optional<Otp> otpInfo = otpRepo.findByOtpAndIsdn(otp, isdn);

            if (otpInfo.isPresent()) {
                Otp storedOtp = otpInfo.get();
                if (!storedOtp.isUsed() && storedOtp.getExpirationTime().isAfter(LocalDateTime.now())) {
                    storedOtp.setUsed(true);
                    return otpRepo.save(storedOtp);
                } else {
                    throw new CommonException(ErrorCode.OTP_WRONG);
                }
            }
            throw new CommonException(ErrorCode.OTP_WRONG);
        }
    }

    // Generate a random 5-digit OTP
    private String generateRandomOtp() {
        Random random = new Random();
        return String.format("%05d", random.nextInt(100000));
    }

    private void sendOtpToUser(String isdn, String otp, LocalDateTime expirationTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedExpirationTime = expirationTime.format(formatter);

        sendSMSService.sendSMS("VIP Subscriber",
                MytelUtils.toMsisdn(isdn),
                "VIP Management: Your OTP is " + otp + ".This OTP will be expired after " + formattedExpirationTime);
        System.out.println("Sending OTP: " + otp + " to PhoneNumber: " + isdn);
    }
}
