package tech.qizz.core.module.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.QuestionHistory;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizJoinedUser;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AllParticipantQuizResponse {
    @JsonProperty("data")
    private List<ParticipantQuizResponse> data;

    @JsonProperty("total")
    private Long total;

    public static AllParticipantQuizResponse of(List<QuizJoinedUser> quizzes) {
        return AllParticipantQuizResponse.builder()
                .data(quizzes.stream().map(quiz -> (ParticipantQuizResponse.of(quiz.getQuestionHistories()))).toList())
                .total((long) quizzes.size())
                .build();
    }
}
