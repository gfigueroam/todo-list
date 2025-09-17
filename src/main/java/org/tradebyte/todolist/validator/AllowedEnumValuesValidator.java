package org.tradebyte.todolist.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.tradebyte.todolist.repository.entity.Status;

import java.util.Arrays;

public class AllowedEnumValuesValidator implements ConstraintValidator<AllowedEnumValues, Status> {
    private Status[] allowedValues;

    @Override
    public void initialize(AllowedEnumValues constraintAnnotation) {
        allowedValues = constraintAnnotation.allowed();
    }

    @Override
    public boolean isValid(Status value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return Arrays.asList(allowedValues).contains(value);
    }
}