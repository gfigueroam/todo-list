package org.tradebyte.todolist.rest.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.tradebyte.todolist.repository.entity.Status;
import org.tradebyte.todolist.validator.AllowedEnumValues;
import org.tradebyte.todolist.validator.AtLeastOneNotNull;

@Data
@AtLeastOneNotNull(fields = {"description", "status"})
public class PatchItemDto {
    @Size(max = 255)
    private String description;

    @AllowedEnumValues(allowed = {Status.DONE, Status.NOT_DONE})
    private Status status;
}
