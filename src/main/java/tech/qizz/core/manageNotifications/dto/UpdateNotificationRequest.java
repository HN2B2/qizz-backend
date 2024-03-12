package tech.qizz.core.manageNotifications.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateNotificationRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;
}
