package tech.qizz.core.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSubCategoryToBankRequest {

    @JsonProperty("subCategories")
    private List<Long> subCategories;
}
