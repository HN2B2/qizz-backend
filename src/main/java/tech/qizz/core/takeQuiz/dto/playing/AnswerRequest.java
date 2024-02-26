package tech.qizz.core.takeQuiz.dto.playing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.constant.QuestionType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    @JsonProperty("answerMetadata")
    private String answerMetadata;

    @NotBlank
    @JsonProperty("questionType")
    private QuestionType questionType;

    @JsonProperty("answerTime")
    private Double answerTime;
}
