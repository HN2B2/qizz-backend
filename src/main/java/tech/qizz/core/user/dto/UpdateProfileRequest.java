package tech.qizz.core.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    @NotBlank
    @JsonProperty("displayName")
    private String displayName;

    @NotBlank
    @JsonProperty("username")
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String username;
}