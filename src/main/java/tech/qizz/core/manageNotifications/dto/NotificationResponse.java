package tech.qizz.core.manageNotifications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.Builder;
import tech.qizz.core.entity.Notification;
import tech.qizz.core.entity.constant.NotificationTargetType;

import java.util.Date;

@Builder
public class NotificationResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @Lob
    @JsonProperty("content")
    private String content;

    @JsonProperty("targetType")
    private NotificationTargetType targetType;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("modifiedAt")
    private Date modifiedAt;

    @JsonProperty("createdBy")
    private long createdBy;

    public static  NotificationResponse of(Notification notification) {
        return (NotificationResponse.builder()
            .id(notification.getNotificationId())
            .title(notification.getTitle())
            .content(notification.getContent())
            .targetType(notification.getTargetType())
            .createdAt(notification.getCreatedAt())
            .modifiedAt(notification.getModifiedAt())
            .createdBy(notification.getCreatedBy().getUserId())
            .build());
    }

}
