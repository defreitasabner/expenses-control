package freitas.abner.expenses.domain.expense;

import freitas.abner.expenses.domain.category.dtos.ReadCategoryData;
import freitas.abner.expenses.domain.user.ReadUserData;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReadExpenseData(
        Long id,
        String description,
        BigDecimal amount,
        LocalDateTime datetime,
        ReadCategoryData category,
        ReadUserData user
) {
    public ReadExpenseData(Expense expense) {
        this(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDatetime(),
                new ReadCategoryData(expense.getCategory()),
                new ReadUserData(expense.getUser())
        );
    }
}
