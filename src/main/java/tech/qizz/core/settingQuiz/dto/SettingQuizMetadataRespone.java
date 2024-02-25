package tech.qizz.core.settingQuiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tech.qizz.core.entity.QuizSetting;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingQuizMetadataRespone {
    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    public static SettingQuizMetadataRespone of(QuizSetting quizSetting) {
        return SettingQuizMetadataRespone.builder()
                .key(quizSetting.getKey())
                .value(quizSetting.getValue())
                .build();
    }
}
