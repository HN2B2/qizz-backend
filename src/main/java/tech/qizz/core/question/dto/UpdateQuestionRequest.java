package tech.qizz.core.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UpdateQuestionRequest {
    @NotBlank
    @JsonProperty("content")
    private String content;
    @JsonProperty("point")
    private Long point;
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
}
