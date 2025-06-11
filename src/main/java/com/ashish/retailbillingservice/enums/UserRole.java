package com.ashish.retailbillingservice.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    USER("USER", "ROLE_USER"),
    ADMIN("ADMIN", "ROLE_ADMIN"),;

    private final String key;
    private final String value;

    public static boolean isValid(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.key.equalsIgnoreCase(role)) return true;
        }
        return false;
    }

    public static String getUserRole(String user) {
        if (user == null || user.isBlank()) {
            throw new IllegalArgumentException("User cannot be null or blank");
        }
        for (UserRole userRole : UserRole.values()) {
            if (userRole.key.equalsIgnoreCase(user)) {
                return userRole.value;
            }
        }
        throw new IllegalArgumentException("Invalid user: " + user);
    }

    public static String getUserType(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or blank");
        }
        for (UserRole userRole : UserRole.values()) {
            if (userRole.value.equalsIgnoreCase(role)) {
                return userRole.key;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }
}

