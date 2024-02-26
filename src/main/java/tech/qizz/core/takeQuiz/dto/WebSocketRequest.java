package tech.qizz.core.takeQuiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
public class WebSocketRequest<T> {

    @NotNull
    @JsonProperty("token")
    private String token;

    @NotNull
    @JsonProperty("body")
    private T data;
}
