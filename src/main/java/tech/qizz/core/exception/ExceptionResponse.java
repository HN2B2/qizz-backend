package tech.qizz.core.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private String error;
    private int status;

    public ExceptionResponse(String message, String error, HttpStatus status) {
        this.message = message;
        this.error = error;
        this.status = status.value();
    }
}
