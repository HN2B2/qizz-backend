package tech.qizz.core.user.constant;

import java.util.Arrays;

public enum UserRole {
    GUEST,
    USER,
    STAFF,
    ADMIN;

    public static UserRole validateUserRole(String role) {
        return Arrays.stream(UserRole.values()).filter(userRole -> userRole.name().equalsIgnoreCase(role)).findFirst().orElse(UserRole.GUEST);
    }
}
