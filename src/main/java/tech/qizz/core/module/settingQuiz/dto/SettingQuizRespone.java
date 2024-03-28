package tech.qizz.core.module.settingQuiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.constant.QuizState;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingQuizRespone {

    @JsonProperty("quizId")
    private Long quizId;

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

    public static SettingQuizRespone of(Quiz quiz) {
        return SettingQuizRespone.builder()
                .quizName(quiz.getName())
                .description(quiz.getDescription())
                .featuredImage(quiz.getFeaturedImage())
                .code(quiz.getCode())
                .quizState(quiz.getQuizState())
                .createdBy(quiz.getCreatedBy().getUserId())
                .bankId(quiz.getQuizBank().getQuizBankId())
                .quizId(quiz.getQuizId())
                .metadata(
                        Optional.ofNullable(quiz.getQuizSettings())
                                .map(metadatas -> metadatas.stream().map(SettingQuizMetadataRespone::of).toList())
                                .orElse(Collections.emptyList())
                )
                .build();
    }
}
