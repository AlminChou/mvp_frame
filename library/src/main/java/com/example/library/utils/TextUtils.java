package com.example.library.utils;

import java.util.regex.Pattern;

/**
 * Created by almin on 2017/12/12.
 */

public class TextUtils {
    private static final Pattern PATTERN_VALID_EMAIL_ADDRESS = Pattern.compile("^[a-zA-Z0-9+][+\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");


    public static boolean isValidEmail(String email) {
        return !isTextEmptyOrNull(email) && PATTERN_VALID_EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isTextEmptyOrNull(String text) {
        return text == null || text.isEmpty() || "null".equalsIgnoreCase(text) || "(null)".equals(text);
    }

    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty() || text.equals("");
    }

    public static boolean isEmptyJsonField(String value) {
        return isTextEmptyOrNull(value) || value.equals("[]") || value.equals("{}");
    }

    public static String getDefaultValueIfNeed(String value) {
        return isTextEmptyOrNull(value) ? "" : value;
    }
}