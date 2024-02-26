package tech.qizz.core.settingQuiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SettingQuizRequest {

    @NotNull
    @JsonProperty("metadata")
    private List<SettingQuizMetadataRequest> metadata;

}
