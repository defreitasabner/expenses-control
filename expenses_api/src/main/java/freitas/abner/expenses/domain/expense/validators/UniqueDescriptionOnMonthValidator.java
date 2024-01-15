package freitas.abner.expenses.domain.expense.validators;

import freitas.abner.expenses.domain.expense.CreateExpenseData;
import freitas.abner.expenses.domain.expense.ExpenseRepository;
import freitas.abner.expenses.domain.expense.UpdateExpenseData;
import freitas.abner.expenses.domain.user.User;
import freitas.abner.expenses.exceptions.SameDescriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Component
public class UniqueDescriptionOnMonthValidator implements ExpenseValidator {

    @Autowired
    private ExpenseRepository repository;

    @Override
    public void validate(CreateExpenseData expenseData, User user) {
        var firstDay = LocalDateTime.of(
                LocalDate.of(
                        expenseData.datetime().getYear(),
                        expenseData.datetime().getMonth(),
                        1),
                LocalTime.of(0, 0, 0)
        );
        var lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        var monthExpenses = repository.findAllByDatetimeBetweenAndUserId(firstDay, lastDay, user.getId());
        if(monthExpenses.stream().anyMatch(
                monthExpense -> monthExpense.getDescription().equals(expenseData.description())
        )) {
            throw new SameDescriptionException("Two different expenses could not have the same description on same month.");
        }
    }
}
