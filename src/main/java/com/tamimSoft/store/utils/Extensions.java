package com.tamimSoft.store.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Extensions {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public static boolean isEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
