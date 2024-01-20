package tech.qizz.core.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.User;
import tech.qizz.core.user.constant.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {

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

    public static UserResponse of(User user) {
        return UserResponse.builder()
            .id(user.getUserId())
            .username(user.getUsername())
            .email(user.getEmail())
            .displayName(user.getDisplayName())
            .role(user.getRole())
            .build();
    }
}
