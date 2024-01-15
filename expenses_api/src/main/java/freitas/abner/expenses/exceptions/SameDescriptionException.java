package freitas.abner.expenses.exceptions;

public class SameDescriptionException extends RuntimeException {
    public SameDescriptionException(String message) {
        super(message);
    }
}
