package tech.qizz.core.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("role")
    private UserRole role;
    @JsonProperty("banned")
    private Boolean banned;

    public static UsersResponse of(User user) {
        return UsersResponse.builder()
            .id(user.getUserId())
            .username(user.getObjectUsername())
            .email(user.getEmail())
            .displayName(user.getDisplayName())
            .role(user.getRole())
            .banned(user.getBanned())
            .build();
    }
}
