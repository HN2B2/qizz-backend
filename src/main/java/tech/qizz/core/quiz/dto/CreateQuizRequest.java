package tech.qizz.core.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CreateQuizRequest {

    @NotBlank
    @JsonProperty("quizName")
    private String quizName;

    @NotBlank
    @JsonProperty("description")
    private String description;

    @NotBlank
    @JsonProperty("featuredImage")
    private String featuredImage;
}
