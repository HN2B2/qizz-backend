package tech.qizz.core.module.question.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.constant.QuestionType;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class QuestionResponse {

    private Long questionId;
    private String content;
    private Long point;
    private int duration;
    private QuestionType type;
    private String answersMetadata;
    private String correctAnswersMetadata;
    private String explainAnswer;
    private Date createdAt;
    private Date modifiedAt;
    private int questionIndex;
    private boolean disabled;
    private Long quizBankId;

    public static QuestionResponse of(Question question) {
        return QuestionResponse.builder()
            .questionId(question.getQuestionId())
            .content(question.getContent())
            .point(question.getPoint())
            .duration(question.getDuration())
            .type(question.getType())
            .answersMetadata(question.getAnswersMetadata())
            .correctAnswersMetadata(question.getCorrectAnswersMetadata())
            .explainAnswer(question.getExplainAnswer())
            .createdAt(question.getCreatedAt())
            .modifiedAt(question.getModifiedAt())
            .questionIndex(question.getQuestionIndex())
            .disabled(question.getDisabled())
            .quizBankId(question.getQuizBank().getQuizBankId())
            .build();
    }

}
