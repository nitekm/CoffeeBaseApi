package coffeebase.api.exceptions.processing;

import coffeebase.api.domain.base.model.error.ErrorResponse;
import coffeebase.api.exceptions.exception.BrewUpdateInterrupted;
import coffeebase.api.exceptions.exception.DeleteUnsuccessful;
import coffeebase.api.exceptions.exception.FileException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.SizeLimitExceededException;
import java.time.LocalDateTime;


@RestControllerAdvice(annotations = ExceptionProcessing.class)
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        return logExceptionAndRespond(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException e) {
        return logExceptionAndRespond(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        return logExceptionAndRespond(e, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    ResponseEntity<ErrorResponse> handleSizeLimitExceededException(SizeLimitExceededException e) {
        return logExceptionAndRespond(e, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(FileException.class)
    ResponseEntity<ErrorResponse> handleFileLoadException(FileException e) {
        return logExceptionAndRespond(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalAccessException.class)
    ResponseEntity<ErrorResponse> handleIllegalAccess(IllegalAccessException e) {
        return logExceptionAndRespond(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BrewUpdateInterrupted.class)
    ResponseEntity<ErrorResponse> handleBrewUpdateInterrupted(BrewUpdateInterrupted e) {
        return logExceptionAndRespond(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DeleteUnsuccessful.class)
    ResponseEntity<ErrorResponse> handleDeleteUnsuccessful(DeleteUnsuccessful e) {
        return logExceptionAndRespond(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> logExceptionAndRespond(Exception e, HttpStatus httpStatus) {
        logger.error("An error has occurred", e);
        var errorResponse = new ErrorResponse(LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
