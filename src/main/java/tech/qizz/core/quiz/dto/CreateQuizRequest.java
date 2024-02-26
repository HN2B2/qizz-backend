package tech.qizz.core.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CreateQuizRequest {

    @NotBlank
//    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @JsonProperty("quizName")
    private String quizName;

    @NotBlank
//    @Pattern(regexp = "[^\\/:*?\"<>|]")
    @JsonProperty("description")
    private String description;

    @NotBlank
    @JsonProperty("featuredImage")
    private String featuredImage;
}
