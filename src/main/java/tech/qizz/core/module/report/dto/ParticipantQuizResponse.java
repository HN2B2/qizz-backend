package tech.qizz.core.module.report.dto;

import lombok.*;
import tech.qizz.core.entity.QuestionHistory;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.User;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantQuizResponse {
    private String displayName;
    private Long userId;
    private Long score;
    private Long quizId;
    private Long point;
    private int totalQuestion;
    private Long quizJoinedUserId;

    public static ParticipantQuizResponse of(List<QuestionHistory> list) {
        return ParticipantQuizResponse.builder()
            .displayName(list.get(0).getQuizJoinedUser().getUser().getDisplayName())
            .userId(list.get(0).getQuizJoinedUser().getUser().getUserId())
            .score(list.stream().map(QuestionHistory::getScore).mapToLong(Long::longValue).sum())
            .quizId(list.get(0).getQuizJoinedUser().getQuiz().getQuizId())
            .point(list.stream().filter(q -> q.getScore() > 0).count())
            .totalQuestion(list.size())
            .quizJoinedUserId(list.get(0).getQuizJoinedUser().getQuizJoinedUserId())
            .build();
    }
}
