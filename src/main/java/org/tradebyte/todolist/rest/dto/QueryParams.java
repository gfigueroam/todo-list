package org.tradebyte.todolist.rest.dto;

import org.tradebyte.todolist.repository.entity.Status;

public record QueryParams(Status status) {
}
