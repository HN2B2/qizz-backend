package tech.qizz.core.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

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
    @JsonProperty("metadata")
    private List<UserMetadataResponse> metadata;

    public static ProfileResponse of(User user) {
        return ProfileResponse.builder()
            .id(user.getUserId())
            .username(user.getObjectUsername())
            .email(user.getEmail())
            .displayName(user.getDisplayName())
            .role(user.getRole())
            .metadata(user.getUserMetadatas().stream().map(UserMetadataResponse::of).toList())
            .build();
    }
}
