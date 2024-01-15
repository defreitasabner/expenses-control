package freitas.abner.expenses.infrastructure.exception;

import freitas.abner.expenses.exceptions.ValidationException;

public record ExceptionDetailData(String detail) {
    public ExceptionDetailData(RuntimeException ex) {
        this(ex.getMessage());
    }
}
