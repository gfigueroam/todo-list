package org.tradebyte.todolist.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.tradebyte.todolist.repository.entity.Status;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowedEnumValuesValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedEnumValues {
    Status[] allowed();

    String message() default "must be one of {allowed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}