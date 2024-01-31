package tech.qizz.core.adviser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.exception.ConflictException;
import tech.qizz.core.exception.ExceptionResponse;
import tech.qizz.core.exception.ForbiddenException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.exception.UnauthorizedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
            "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), "Bad Request",
            HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), "Not Found",
            HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), "Unauthorized",
            HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionResponse> handleForbiddenException(ForbiddenException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), "Forbidden",
            HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionResponse> handleConflictException(ConflictException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), "Conflict",
            HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}