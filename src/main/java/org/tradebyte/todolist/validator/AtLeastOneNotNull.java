package org.tradebyte.todolist.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AtLeastOneNotNullValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneNotNull {
    String message() default "At least one field must be not null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields(); // List of fields to check
}
