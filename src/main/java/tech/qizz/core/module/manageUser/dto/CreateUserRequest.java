package tech.qizz.core.module.manageUser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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
public class CreateUserRequest {

    @Email
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6,255}$")
    @JsonProperty("password")
    private String password;

    @NotBlank
    @JsonProperty("username")
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String username;
}
