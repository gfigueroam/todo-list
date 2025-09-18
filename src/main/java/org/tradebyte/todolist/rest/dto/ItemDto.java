package org.tradebyte.todolist.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.tradebyte.todolist.repository.entity.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ItemDto {

    private UUID id;
    private String description;
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("creation_datetime")
    private LocalDateTime creationDatetime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("due_datetime")
    private LocalDateTime dueDatetime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("done_datetime")
    private LocalDateTime doneDatetime;

}
