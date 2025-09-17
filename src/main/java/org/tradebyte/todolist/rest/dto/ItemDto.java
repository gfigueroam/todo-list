package org.tradebyte.todolist.rest.dto;

import lombok.Data;
import org.tradebyte.todolist.repository.entity.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ItemDto {

    private UUID id;
    private String description;
    private Status status;
    private LocalDateTime creationDatetime;
    private LocalDateTime dueDatetime;
    private LocalDateTime doneDatetime;
}
