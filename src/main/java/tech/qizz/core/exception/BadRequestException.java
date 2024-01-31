package tech.qizz.core.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@RequiredArgsConstructor
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
