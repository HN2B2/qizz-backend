package tech.qizz.core.module.takeQuiz.dto.playing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.constant.QuestionType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayingQuestionResponse {

    @JsonProperty("questionId")
    private long questionId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("point")
    private long point;

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("type")
    private QuestionType type;

    @JsonProperty("answersMetadata")
    private String answersMetadata;

    @JsonProperty("correctAnswersMetadata")
    private String correctAnswersMetadata;

    public static PlayingQuestionResponse of(
        Question question,
        boolean withCorrectAnswers
    ) {
        return PlayingQuestionResponse
            .builder()
            .questionId(question.getQuestionId())
            .content(question.getContent())
            .point(question.getPoint())
            .duration(question.getDuration())
            .type(question.getType())
            .answersMetadata(question.getAnswersMetadata())
            .correctAnswersMetadata(
                withCorrectAnswers
                    ? question.getCorrectAnswersMetadata()
                    : null
            )
            .build();
    }
}
