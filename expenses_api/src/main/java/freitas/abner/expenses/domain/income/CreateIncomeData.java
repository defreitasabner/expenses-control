package freitas.abner.expenses.domain.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public record CreateIncomeData(
        @NotBlank
        @Size(min = 1, message = "Description should have at least 1 chars.")
        @Size(max = 255, message = "Description should have less than 255 chars.")
        String description,
        @NotNull
        BigDecimal amount,
        @NotNull
        LocalDateTime datetime
) {
}
