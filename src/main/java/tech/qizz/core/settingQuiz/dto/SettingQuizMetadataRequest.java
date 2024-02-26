package tech.qizz.core.settingQuiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingQuizMetadataRequest {
    @NotBlank
    @JsonProperty("key")
    private String key;

    @NotBlank
    @JsonProperty("value")
    private String value;
}
