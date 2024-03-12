package tech.qizz.core.manageNotifications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tech.qizz.core.entity.constant.NotificationTargetType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {

    @NotBlank
    @JsonProperty("title")
    private String title;

    @NotBlank
    @JsonProperty("content")
    private String content;

    @JsonProperty("targetType")
    private NotificationTargetType targetType;


}
