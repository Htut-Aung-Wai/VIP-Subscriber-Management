package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MytelUtils {

    public static String toIsdn(String number) {
        String isdn = "";
        if (number == null) {
            return isdn;
        } else {
            isdn = number;
        }
        if (number.startsWith("09")) {
            isdn = number.substring(1);
        } else if (number.startsWith("+959")) {
            isdn = number.substring(3);
        } else if (number.startsWith("959")) {
            isdn = number.substring(2);
        }
        return isdn;
    }


    public static String toMsisdn(String number) {
        return "95" + toIsdn(number);
    }


    public static boolean isMytelNumber(String number) {
        String isdn = toIsdn(number);
        if (isdn.startsWith("965") ||
                isdn.startsWith("966") ||
                isdn.startsWith("967") ||
                isdn.startsWith("968") ||
                isdn.startsWith("969")
        ) {
            return true;
        } else return false;
    }


    public static String toJson(Object object) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json;
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("Cannot convert java object to json string", e);
            json = object.toString();
        }
        return json;
    }


    public static String toString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("Cannot convert java object to json string", e);
            return null;
        }
    }


    public static void logProcessTime(String function, LocalDateTime begin, LocalDateTime end) {
        log.info("[{} ms] {}", ChronoUnit.MILLIS.between(begin, end), function);

    }


    public static long getDurationBetween(LocalDateTime from, LocalDateTime to) {
        return ChronoUnit.MILLIS.between(from, to);
    }


    public static String toChar(LocalDateTime time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(dateTimeFormatter);
    }


    public static void sendSms(String source, String dest, String content) {
        LocalDateTime begin = LocalDateTime.now();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("bulksms", "bulksms");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> payload = new HashMap<>();
        payload.put("address", source);
        payload.put("content", content);
        payload.put("number", dest);
        log.info("Send SMS payload: {}", payload);
        HttpEntity<?> request = new HttpEntity<>(payload, httpHeaders);
        try {
            String sendSMSResponse = restTemplate.postForObject("http://10.201.3.251:9898/smppgw/v1.0/action/submit",
                    request, String.class);
            log.info("Send SMS response: {}", sendSMSResponse);
        } catch (Exception e) {
            log.error("Send SMS error ", e);
        }
        LocalDateTime end = LocalDateTime.now();
        logProcessTime("send sms", begin, end);
    }


    public static String getRedissonLockKey(String... strs) {
        return String.join("_", strs);
    }
}
