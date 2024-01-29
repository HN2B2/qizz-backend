package tech.qizz.core.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.UserMetadata;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMetadataResponse {

    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    public static UserMetadataResponse of(UserMetadata userMetadata) {
        return UserMetadataResponse.builder()
            .key(userMetadata.getKey())
            .value(userMetadata.getValue())
            .build();
    }
}
