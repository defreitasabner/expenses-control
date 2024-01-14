package freitas.abner.expenses.domain.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateExpenseData(
        String description,
        BigDecimal amount,
        LocalDateTime datetime
) {
}
