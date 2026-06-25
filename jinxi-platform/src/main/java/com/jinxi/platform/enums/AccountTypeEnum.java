package com.jinxi.platform.enums;

import java.util.regex.Pattern;

public enum AccountTypeEnum {
    USERNAME,
    EMAIL,
    PHONE;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    public static AccountTypeEnum resolve(String account) {
        if (account == null) {
            return USERNAME;
        }
        if (EMAIL_PATTERN.matcher(account).matches()) {
            return EMAIL;
        }
        if (PHONE_PATTERN.matcher(account).matches()) {
            return PHONE;
        }
        return USERNAME;
    }
}
