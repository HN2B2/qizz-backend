package tech.qizz.core.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.QuizQuestion;
import tech.qizz.core.entity.constant.QuestionType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionReportResponse {

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

    @JsonProperty("explainAnswer")
    private String explainAnswer;

    @JsonProperty("questionIndex")
    private Integer questionIndex;

    @JsonProperty("participants")
    private List<ParticipantResponse> participants;

    public static QuestionReportResponse of(QuizQuestion quizQuestion) {
        return QuestionReportResponse.builder()
            .questionId(quizQuestion.getQuestion().getQuestionId())
            .content(quizQuestion.getQuestion().getContent())
            .point(quizQuestion.getQuestion().getPoint())
            .duration(quizQuestion.getQuestion().getDuration())
            .type(quizQuestion.getQuestion().getType())
            .answersMetadata(quizQuestion.getQuestion().getAnswersMetadata())
            .correctAnswersMetadata(quizQuestion.getQuestion().getCorrectAnswersMetadata())
            .explainAnswer(quizQuestion.getQuestion().getExplainAnswer())
            .questionIndex(quizQuestion.getQuestion().getQuestionIndex())
            .participants(
                quizQuestion.getQuestionHistories().stream().map(ParticipantResponse::of).toList())
            .build();

    }
}
