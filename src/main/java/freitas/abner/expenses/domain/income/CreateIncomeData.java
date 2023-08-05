package freitas.abner.expenses.domain.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public record CreateIncomeData(
        @NotBlank
        String description,
        @NotNull
        BigDecimal amount,
        @NotNull
        LocalDateTime datetime
) {
}
