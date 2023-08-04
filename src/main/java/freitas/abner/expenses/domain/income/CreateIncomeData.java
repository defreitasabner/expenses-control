package freitas.abner.expenses.domain.income;

import java.math.BigDecimal;
import java.util.Date;

public record CreateIncomeData(
        String description,
        BigDecimal amount,
        Date datetime
) {
}
