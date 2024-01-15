package freitas.abner.expenses.infrastructure.exception;

import freitas.abner.expenses.exceptions.InvalidCategoryException;
import freitas.abner.expenses.exceptions.SameDescriptionException;
import freitas.abner.expenses.exceptions.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorData>> error400(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(fieldErrors.stream().map(ValidationErrorData::new).toList());
    }

    public record ValidationErrorData(String field, String message) {
        public ValidationErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(SameDescriptionException.class)
    public ResponseEntity<ExceptionDetailData> sameDescriptionException(SameDescriptionException ex) {
        var exceptionDto = new ExceptionDetailData(ex);
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<ExceptionDetailData> invalidCategoryException(InvalidCategoryException ex) {
        var exceptionDto = new ExceptionDetailData(ex);
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionDetailData> validationException(ValidationException ex) {
        var exceptionDto = new ExceptionDetailData(ex);
        return ResponseEntity.badRequest().body(exceptionDto);
    }

}
