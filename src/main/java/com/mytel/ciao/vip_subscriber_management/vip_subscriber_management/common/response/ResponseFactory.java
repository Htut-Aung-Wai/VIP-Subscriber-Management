package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {
    public ResponseEntity<Basic> buildError(HttpStatus status, Object result, String errorCode, String message) {
        Basic basic = new Basic();
        basic.setSuccess(false);
        basic.setResult(result);
        basic.setMessage(message);
        basic.setCode(errorCode);
        return ResponseEntity.status(status).body(basic);
    }

    public ResponseEntity<Basic> buildSuccess(HttpStatus status, Object result, String errorCode, String message) {
        Basic basic = new Basic();
        basic.setSuccess(true);
        basic.setMessage(message);
        basic.setResult(result);
        basic.setCode(errorCode);
        return ResponseEntity.status(status).body(basic);
    }
    public ResponseEntity<Object> badRequest(HttpStatus status, String errorCode, String message) {
        Basic basic = new Basic();
        basic.setSuccess(false);
        basic.setResult(null);
        basic.setMessage(message);
        basic.setCode(errorCode);
        return ResponseEntity.status(status).body(basic);
    }
}
