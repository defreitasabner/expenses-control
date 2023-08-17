package freitas.abner.expenses.domain.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReadExpenseData(
        Long id,
        String description,
        BigDecimal amount,
        LocalDateTime datetime
) {
    public ReadExpenseData(Expense expense) {
        this(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getDatetime());
    }
}
