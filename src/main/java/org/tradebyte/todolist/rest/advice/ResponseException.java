package org.tradebyte.todolist.rest.advice;

import java.time.ZonedDateTime;
import java.util.List;

public record ResponseException (
        String path,
        //String message,
        int statusCode,
        ZonedDateTime zonedDateTime,
        List<String> errors

){ }
