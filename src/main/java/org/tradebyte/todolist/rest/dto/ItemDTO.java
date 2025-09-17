package org.tradebyte.todolist.rest.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ItemDTO {

    private UUID id;

    @NotBlank
    @Size(max = 255)
    private String description;

    @FutureOrPresent
    private LocalDateTime dueDatetime;

}
