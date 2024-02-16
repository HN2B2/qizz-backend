package tech.qizz.core.takeQuiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRoomResponse {

    @JsonProperty("username")
    private String username;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("avatar")
    private String avatar;

    public static UserJoinRoomResponse of(User user) {
        String avatar =
            Objects.requireNonNull(
                    user
                        .getUserMetadatas()
                        .stream()
                        .filter(metadata -> metadata.getKey().equals("avatarUrl"))
                        .findFirst()
                        .orElse(null))
                .getValue();

        return UserJoinRoomResponse.builder()
            .username(user.getUsername())
            .displayName(user.getDisplayName())
            .avatar(avatar)
            .build();
    }
}
