package tech.qizz.core.takeQuiz.dto.waitingRoom;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.User;
import tech.qizz.core.user.dto.UserMetadataResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomUserResponse {

    @JsonProperty("email")
    private String email;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("metadata")
    private List<UserMetadataResponse> metadata;

    public static RoomUserResponse of(User user) {
        return RoomUserResponse.builder()
            .email(user.getEmail())
            .displayName(user.getDisplayName())
            .metadata(
                Optional.ofNullable(user.getUserMetadatas())
                    .map(metadatas -> metadatas.stream().map(UserMetadataResponse::of).toList())
                    .orElse(Collections.emptyList())
            ).build();
    }
}
