package tech.qizz.core.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSubCategoryToBankRequest {
    @NotBlank
    @JsonProperty("subCategories")
    private List<Long> subCategories;
}
