package freitas.abner.expenses.domain.expense.validators;

import freitas.abner.expenses.domain.expense.CreateExpenseData;
import freitas.abner.expenses.domain.user.User;

public interface ExpenseValidator {
    void validate(CreateExpenseData expenseData, User user);
}
