package tech.qizz.core.module.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
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
public class ReportDetailResponse {

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

    @JsonProperty("questionReports")
    private List<QuestionReportResponse> questionReports;

    public static ReportDetailResponse of(Quiz quiz) {
        return ReportDetailResponse.builder()
            .quizId(quiz.getQuizId())
            .quizName(quiz.getName())
            .quizCode(quiz.getCode())
            .totalParticipant(quiz.getQuizJoinedUsers().size())
            .state(quiz.getQuizState())
            .createdAt(quiz.getCreatedAt())
            .questionReports(
                quiz.getQuizQuestions().stream().map(QuestionReportResponse::of).toList())
            .build();
    }

}
