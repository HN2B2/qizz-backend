package tech.qizz.core.module.report.dto;

import lombok.*;
import tech.qizz.core.entity.QuestionHistory;
import tech.qizz.core.module.question.dto.QuestionResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantQuestionDetailResponse {
    private QuestionResponse question;
    private long score;
    private String answerMetadata;
    private boolean isCorrect;

    public static ParticipantQuestionDetailResponse of(QuestionHistory questionHistory) {
        boolean isCorrect = questionHistory.getQuizQuestion().getQuestion()
            .getCorrectAnswersMetadata().equals(questionHistory.getAnswerMetadata());
        return ParticipantQuestionDetailResponse.builder()
            .question(QuestionResponse.of(questionHistory.getQuizQuestion().getQuestion()))
            .score(questionHistory.getScore())
            .answerMetadata(questionHistory.getAnswerMetadata())
            .isCorrect(isCorrect)
            .build();

    }
}
