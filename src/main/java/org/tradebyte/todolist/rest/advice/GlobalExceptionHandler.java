package org.tradebyte.todolist.rest.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tradebyte.todolist.repository.entity.Status;
import org.tradebyte.todolist.rest.advice.exception.EntityImmutableException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        //TODO: This needs some formatting to return a better message which includes information about the parameter
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed for parameter(s).");
    }

    @ExceptionHandler(EntityImmutableException.class)
    public ResponseEntity<?> handleEntityImmutableException(EntityImmutableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<?> handleConversionFailedException(ConversionFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status not valid use: " + Status.values());
    }

}
