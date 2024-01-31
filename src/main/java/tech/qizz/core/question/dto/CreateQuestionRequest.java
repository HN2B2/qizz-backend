package tech.qizz.core.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CreateQuestionRequest {
    @NotBlank
    @JsonProperty("content")
    private String content;
//    @Positive
//    @NotBlank
//    @Min(1)
    @JsonProperty("point")
    private Long point;
//    @Positive
//    @Pattern(regexp = "^(15|30|60|120)$")
    @JsonProperty("duration")
//    @NotBlank
    private int duration;
    @NotBlank
    @JsonProperty("type")
    private String type;

    @JsonProperty("answersMetadata")
    private String answersMetadata;
    @NotBlank
    @JsonProperty("correctAnswersMetadata")
    private String correctAnswersMetadata;
    @JsonProperty("explainAnswer")
    private String explainAnswer;
    @JsonProperty("questionIndex")
    private int questionIndex;
    @JsonProperty("disabled")
    private boolean disabled;
    @JsonProperty("quizBankId")
    private Long quizBankId;
}
