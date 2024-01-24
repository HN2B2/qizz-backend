package tech.qizz.core.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBankRequest {
    @NotBlank
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("featuresImage")
    private String featuresImage;
    @JsonProperty("quizPublicity")
    private Boolean quizPublicity;
    @JsonProperty("publicEditable")
    private Boolean publicEditable;
    @JsonProperty("draft")
    private Boolean draft;
}
