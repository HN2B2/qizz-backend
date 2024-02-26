package tech.qizz.core.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.Quiz;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllReportResponse {

    @JsonProperty("data")
    private List<ReportResponse> data;

    @JsonProperty("total")
    private Long total;

    public static GetAllReportResponse of(Page<Quiz> quizzes) {
        return GetAllReportResponse.builder()
            .data(quizzes.stream().map(ReportResponse::of).toList())
            .total(quizzes.getTotalElements())
            .build();
    }
}
