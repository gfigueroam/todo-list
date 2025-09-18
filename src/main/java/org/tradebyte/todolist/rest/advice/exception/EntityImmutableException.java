package org.tradebyte.todolist.rest.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityImmutableException extends RuntimeException {
    public EntityImmutableException(String message) {
        super(message);
    }
}
