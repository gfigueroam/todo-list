package org.tradebyte.todolist.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.tradebyte.todolist.repository.entity.Item;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CreateItemDto {

    @NotBlank(message = "Description can not be blank")
    @Size(max = 255, message = "Max size of the description is 255")
    private String description;

    @JsonProperty("due_datetime")
    @NotNull
    @FutureOrPresent(message = "Due datetime can not be in the past")
    private LocalDateTime dueDatetime;

    public Item ToItem() {
        return Item.builder()
                .description(description)
                .dueDatetime(dueDatetime)
                .build();
    }

}
