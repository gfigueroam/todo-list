package org.tradebyte.todolist.rest.advice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tradebyte.todolist.repository.entity.Status;
import org.tradebyte.todolist.rest.advice.exception.EntityImmutableException;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseException> handleEntityNotFound(EntityNotFoundException e, HttpServletRequest request) {

        ResponseException responseException = new ResponseException(
                request.getContextPath(),
                //e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(),
                List.of()
                );
        return new ResponseEntity<>(responseException,(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<String> errors = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ResponseException responseException = new ResponseException(
                request.getContextPath(),
                //e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                errors
        );

        return new ResponseEntity<>(responseException,(HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(EntityImmutableException.class)
    public ResponseEntity<?> handleEntityImmutableException(EntityImmutableException e, HttpServletRequest request) {
        ResponseException responseException = new ResponseException(
                request.getContextPath(),
                //e.getMessage(),
                HttpStatus.CONFLICT.value(),
                ZonedDateTime.now(),
                List.of(e.getMessage())
        );
        return new ResponseEntity<>(responseException,(HttpStatus.CONFLICT));
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<?> handleConversionFailedException(ConversionFailedException e, HttpServletRequest request) {
        ResponseException responseException = new ResponseException(
                request.getContextPath(),
                //e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                List.of("Status not valid use: " + Arrays.toString(Status.values()))
        );

        return new ResponseEntity<>(responseException,(HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseException> handleException(Exception e, HttpServletRequest request) {
        ResponseException responseException = new ResponseException(
                request.getContextPath(),
               // e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(),
                List.of()
        );
        return new ResponseEntity<>(responseException,(HttpStatus.INTERNAL_SERVER_ERROR));
    }



}
