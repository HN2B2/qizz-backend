package tech.qizz.core.module.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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
public class UpsertProfileMetadataRequest {

    @NotBlank
    @JsonProperty("key")
    private String key;

    @NotBlank
    @JsonProperty("value")
    private String value;
}
