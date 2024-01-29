package tech.qizz.core.manageCategory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    @NotBlank
    @JsonProperty("name")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]{1,255}$")
    private String name;

    @NotBlank
    @JsonProperty("description")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-.,!?]{0,255}$")
    private String description;
}
