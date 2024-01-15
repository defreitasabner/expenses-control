package freitas.abner.expenses.domain.expense;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DeleteExpenseData(
        @NotBlank
        String description
) {
}
