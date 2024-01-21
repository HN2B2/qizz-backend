package tech.qizz.core.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@RequiredArgsConstructor
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
