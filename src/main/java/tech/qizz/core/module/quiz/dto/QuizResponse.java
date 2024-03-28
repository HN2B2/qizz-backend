package tech.qizz.core.module.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.constant.QuizState;
import tech.qizz.core.module.settingQuiz.dto.SettingQuizMetadataRespone;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    @JsonProperty("quizId")
    private long quizId;

    @JsonProperty("quizName")
    private String quizName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("featuredImage")
    private String featuredImage;

    @JsonProperty("code")
    private String code;

    @JsonProperty("quizState")
    private QuizState quizState;

    @JsonProperty("createdBy")
    private Long createdBy;

    @JsonProperty("bankId")
    private Long bankId;

    @JsonProperty("metadata")
    private List<SettingQuizMetadataRespone> metadata;
    public static QuizResponse of(Quiz quiz) {
        return QuizResponse
            .builder()
            .quizId(quiz.getQuizId())
            .quizName(quiz.getName())
            .description(quiz.getDescription())
            .featuredImage(quiz.getFeaturedImage())
            .code(quiz.getCode())
            .quizState(quiz.getQuizState())
                .createdBy(quiz.getCreatedBy().getUserId())
            .bankId(quiz.getQuizBank().getQuizBankId())
                .metadata(
                        Optional.ofNullable(quiz.getQuizSettings())
                                .map(metadatas -> metadatas.stream().map(SettingQuizMetadataRespone::of).toList())
                                .orElse(Collections.emptyList())
                )
            .build();
    }
}
