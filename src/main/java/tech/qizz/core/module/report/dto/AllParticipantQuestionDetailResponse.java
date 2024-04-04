package tech.qizz.core.module.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.QuestionHistory;
import tech.qizz.core.entity.QuizJoinedUser;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllParticipantQuestionDetailResponse {
    @JsonProperty("data")
    private List<ParticipantQuestionDetailResponse> data;

    @JsonProperty("total")
    private Long total;

    public static AllParticipantQuestionDetailResponse of(List<QuestionHistory> quizzes) {
        return AllParticipantQuestionDetailResponse.builder()
                .data(quizzes.stream().map(quiz -> (ParticipantQuestionDetailResponse.of(quiz))).toList())
                .total((long) quizzes.size())
                .build();
    }
}
