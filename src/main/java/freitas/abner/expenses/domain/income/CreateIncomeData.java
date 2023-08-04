package freitas.abner.expenses.domain.income;

import java.util.Date;

public record CreateIncomeData(
        String description,
        Long value,
        Date datetime
) {
}
