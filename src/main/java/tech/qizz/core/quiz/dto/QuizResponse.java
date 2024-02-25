package tech.qizz.core.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.constant.QuizState;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    @JsonProperty("quizId")
    private long quizId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("featuredImage")
    private String featuredImage;

    @JsonProperty("code")
    private String code;

    @JsonProperty("quizState")
    private QuizState quizState;

    public static QuizResponse of(Quiz quiz) {
        return QuizResponse
            .builder()
            .quizId(quiz.getQuizId())
            .name(quiz.getName())
            .description(quiz.getDescription())
            .featuredImage(quiz.getFeaturedImage())
            .code(quiz.getCode())
            .quizState(quiz.getQuizState())
            .build();
    }
}
