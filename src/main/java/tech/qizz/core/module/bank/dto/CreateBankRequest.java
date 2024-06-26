package tech.qizz.core.module.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tech.qizz.core.module.manageBank.dto.CreateManageBankRequest;

import java.util.List;

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
    @JsonProperty("manageBanks")
    private List<CreateManageBankRequest> manageBanks;

//    @JsonProperty("subCategories")
//    private List<Long> subCategories;
//còn list subcategories nữa

}
