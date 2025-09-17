package org.tradebyte.todolist.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateItemDto {

    @NotBlank(message = "Description can not be blank")
    @Size(max = 255, message = "Max size of the description is 255")
    private String description;

    @JsonProperty("due_datetime")
    @FutureOrPresent(message = "Due datetime can not be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime dueDatetime;

}
