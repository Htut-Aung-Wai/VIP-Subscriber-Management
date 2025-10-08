package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.exception;


import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.utils.I18n;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommonException extends RuntimeException {
    private String errorCode;
    private String message;

    public CommonException(String requestId, String errorCode) {
        this.errorCode = errorCode;
        this.message = I18n.t(errorCode);
    }

    public CommonException(String errorCode) {
        this.errorCode = errorCode;
        this.message = I18n.t(errorCode);
    }
}

