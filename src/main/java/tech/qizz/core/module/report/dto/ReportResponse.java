package tech.qizz.core.module.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
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
public class ReportResponse {

    @JsonProperty("quizId")
    private long quizId;

    @JsonProperty("quizName")
    private String quizName;

    @JsonProperty("quizCode")
    private String quizCode;

    @JsonProperty("totalParticipant")
    private int totalParticipant;

    @JsonProperty("state")
    private QuizState state;

    @JsonProperty("createdAt")
    private Date createdAt;

    public static ReportResponse of(Quiz quiz) {
        return ReportResponse.builder()
            .quizId(quiz.getQuizId())
            .quizName(quiz.getName())
            .quizCode(quiz.getCode())
            .state(quiz.getQuizState())
            .totalParticipant(quiz.getQuizJoinedUsers().size())
            .createdAt(quiz.getCreatedAt())
            .build();
    }
}
