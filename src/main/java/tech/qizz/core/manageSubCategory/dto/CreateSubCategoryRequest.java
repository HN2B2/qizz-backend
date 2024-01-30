package tech.qizz.core.manageSubCategory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubCategoryRequest {
    @NotBlank
    @JsonProperty("name")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]{1,255}$")
    private String name;

    @NotBlank
    @JsonProperty("description")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-.,!?]{0,255}$")
    private String description;

}
