package tech.qizz.core.module.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.QuestionHistory;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponse {

    private long id;
    private String displayName;
    private long score;
    private double answerTime;
    private String answerMetadata;
    private boolean isCorrect;

    public static ParticipantResponse of(QuestionHistory questionHistory) {
        boolean isCorrect = questionHistory.getQuizQuestion().getQuestion()
            .getCorrectAnswersMetadata().equals(questionHistory.getAnswerMetadata());
        return ParticipantResponse.builder()
            .id(questionHistory.getQuizJoinedUser().getUser().getUserId())
            .displayName(questionHistory.getQuizJoinedUser().getUser().getDisplayName())
            .score(questionHistory.getScore())
            .answerTime(questionHistory.getAnswerTime())
            .answerMetadata(questionHistory.getAnswerMetadata())
            .isCorrect(isCorrect)
            .build();

    }
}
