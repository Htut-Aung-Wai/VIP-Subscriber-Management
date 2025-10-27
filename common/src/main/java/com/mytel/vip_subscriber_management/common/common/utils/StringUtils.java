package com.mytel.vip_subscriber_management.common.common.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}
