package coffeebase.api.exceptions.processing;

import coffeebase.api.exceptions.exception.BrewUpdateInterrupted;
import coffeebase.api.exceptions.exception.FileException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.SizeLimitExceededException;


@RestControllerAdvice(annotations = IllegalExceptionProcessing.class)
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        logger.error(e.toString());
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    ResponseEntity<String> handleSizeLimitExceededException(SizeLimitExceededException e) {
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(FileException.class)
    ResponseEntity<String> handleFileLoadException(FileException e) {
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalAccessException.class)
    ResponseEntity<String> handleIllegalAccess(IllegalArgumentException e) {
        logger.error(e.toString());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BrewUpdateInterrupted.class)
    ResponseEntity<String> handleBrewUpdateInterrupted(BrewUpdateInterrupted e) {
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
