package tech.qizz.core.manageNotifications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.Notification;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllNotificationsResponse {

    @JsonProperty("data")
    private List<NotificationResponse> data;
    @JsonProperty("total")
    private Long total;

    public static GetAllNotificationsResponse of(Page<Notification> notifications) {
        return GetAllNotificationsResponse.builder()
            .data(notifications.stream().map(NotificationResponse::of).toList())
            .total(notifications.getTotalElements())
            .build();
    }
}
