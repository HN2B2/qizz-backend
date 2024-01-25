package tech.qizz.core.manageBank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateManageBankRequest {
    @JsonProperty("editable")
    private Boolean editable;
}
