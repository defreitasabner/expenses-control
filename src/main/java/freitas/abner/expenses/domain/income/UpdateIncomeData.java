package freitas.abner.expenses.domain.income;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateIncomeData(
        @NotNull
        Long id,
        String description,
        BigDecimal amount,
        LocalDateTime datetime
) {
}
