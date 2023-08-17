package freitas.abner.expenses.services;

import freitas.abner.expenses.domain.expense.CreateExpenseData;
import freitas.abner.expenses.domain.expense.Expense;
import freitas.abner.expenses.domain.expense.ExpenseRepository;
import freitas.abner.expenses.domain.income.Income;
import freitas.abner.expenses.exceptions.SameDescriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository repository;

    public Expense registerNewExpense(CreateExpenseData expenseDto) throws SameDescriptionException {
        var expense = new Expense(expenseDto);
        var monthExpenses = getAllMonthIncomes(expenseDto.datetime());
        verifyDescription(expense.getDescription(), monthExpenses);
        repository.save(expense);
        return expense;
    }

    private List<Expense> getAllMonthIncomes(LocalDateTime dateTime) {
        var firstDay = LocalDateTime.of(
                LocalDate.of(
                        dateTime.getYear(),
                        dateTime.getMonth(),
                        1),
                LocalTime.of(0, 0, 0)
        );
        var lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        return repository.findAllByDatetimeBetween(firstDay, lastDay);
    }

    private void verifyDescription(String description, List<Expense> monthExpenses) throws SameDescriptionException {
        if(monthExpenses.stream().anyMatch(monthExpense -> monthExpense.getDescription().equals(description)))
            throw new SameDescriptionException();
    }
}
