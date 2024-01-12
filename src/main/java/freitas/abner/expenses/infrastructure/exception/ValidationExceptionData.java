package freitas.abner.expenses.infrastructure.exception;

import freitas.abner.expenses.exceptions.ValidationException;

public record ValidationExceptionData(String detail) {
    public ValidationExceptionData(ValidationException ex) {
        this(ex.getMessage());
    }
}
