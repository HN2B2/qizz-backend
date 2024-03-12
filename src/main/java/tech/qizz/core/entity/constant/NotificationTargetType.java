package tech.qizz.core.entity.constant;

import java.util.Arrays;

public enum NotificationTargetType {
    ALL_PEOPLE,
    STAFF_ONLY,
    USER_ONLY;
public static NotificationTargetType validateNotificationTargetType(String type) {
    return Arrays.stream(NotificationTargetType.values())
        .filter(notificationTargetType -> notificationTargetType.name().equalsIgnoreCase(type)).findFirst()
        .orElse(NotificationTargetType.ALL_PEOPLE);
}
}
