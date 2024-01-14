package freitas.abner.expenses.domain.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateExpenseData(
    @NotBlank
    @Size(min = 1, message = "Description should have at least 1 chars.")
    @Size(max = 255, message = "Description should have less than 255 chars.")
    String description,
    @NotNull
    BigDecimal amount,
    @NotNull
    LocalDateTime datetime,
    @NotNull
    Long categoryId
) { }
