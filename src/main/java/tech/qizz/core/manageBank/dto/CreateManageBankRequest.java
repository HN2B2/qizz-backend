package tech.qizz.core.manageBank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateManageBankRequest {
    @JsonProperty("email")
    @Email
    private String email;
    @JsonProperty("editable")
    private Boolean editable;
}
