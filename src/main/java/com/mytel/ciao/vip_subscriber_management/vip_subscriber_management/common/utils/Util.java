package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.utils;

import java.time.LocalDateTime;

/**
 * Created by Tungct in 12/10/2020
 */
public interface Util {
    String normalizeIsdn(String isdn);
    String toIsdn(String number);
    String toMsisdn(String number);
    boolean isMytelNumber(String number);
    String localDateTimeToString(LocalDateTime dateTime);
    LocalDateTime stringToLocalDateTime (String timeString);
    LocalDateTime longToLocalDateTime (long timeLong);
    CharSequence standardizeDateString(String dateString);
    String toJson(Object object);
    String toJsonPretty(Object object);
    String getTimeCode();
    Long localDateTimeToMiliSecond(LocalDateTime dateTime);
}
