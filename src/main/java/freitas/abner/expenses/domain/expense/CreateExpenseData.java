package freitas.abner.expenses.domain.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateExpenseData(
    @NotBlank
    String description,
    @NotNull
    BigDecimal amount,
    @NotNull
    LocalDateTime datetime,
    @NotNull
    Long categoryId
) { }
