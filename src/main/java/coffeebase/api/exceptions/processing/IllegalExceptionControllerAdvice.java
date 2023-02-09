package coffeebase.api.exceptions.processing;

import coffeebase.api.exceptions.exception.FileLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.SizeLimitExceededException;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice(annotations = IllegalExceptionProcessing.class)
public class IllegalExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(IllegalExceptionControllerAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    ResponseEntity<String> handleSizeLimitExceededException(SizeLimitExceededException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(FileLoadException.class)
    ResponseEntity<String> handleFileLoadException(FileLoadException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
