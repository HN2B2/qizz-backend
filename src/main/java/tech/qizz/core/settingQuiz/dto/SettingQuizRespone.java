package tech.qizz.core.settingQuiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizSetting;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.user.dto.ProfileResponse;
import tech.qizz.core.user.dto.UserMetadataResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingQuizRespone {

    @JsonProperty("quiz_id")
    private Long quiz_id;
    @JsonProperty("metadata")
    private List<SettingQuizMetadataRespone> metadata;

    public static SettingQuizRespone of(Quiz quiz) {
        return SettingQuizRespone.builder()
                .quiz_id(quiz.getQuizId())
                .metadata(
                        Optional.ofNullable(quiz.getQuizSettings())
                                .map(metadatas -> metadatas.stream().map(SettingQuizMetadataRespone::of).toList())
                                .orElse(Collections.emptyList())
                )
                .build();
    }
}
