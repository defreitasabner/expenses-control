package freitas.abner.expenses.services;

import freitas.abner.expenses.domain.expense.*;
import freitas.abner.expenses.exceptions.InvalidCategoryException;
import freitas.abner.expenses.exceptions.SameDescriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    CategoryService categoryService;

    public Expense registerNewExpense(CreateExpenseData expenseDto)
            throws SameDescriptionException, InvalidCategoryException
    {
        var category = categoryService.getCategoryById(expenseDto.categoryId());
        if(category == null) throw new InvalidCategoryException();
        var expense = new Expense(expenseDto, category);
        var monthExpenses = getAllMonthExpenses(expenseDto.datetime());
        verifyDescription(expense.getDescription(), monthExpenses);
        repository.save(expense);
        return expense;
    }

    public Page<ReadExpenseData> getAllExpenseDataPageable(Pageable pageable) {
        return repository.findAll(pageable).map(ReadExpenseData::new);
    }

    public ReadExpenseData getExpenseDetail(Long id) {
        var expense = repository.getReferenceById(id);
        return new ReadExpenseData(expense);
    }

    public ReadExpenseData updateExpense(Long id, UpdateExpenseData updateDto) throws SameDescriptionException {
        var expense = repository.getReferenceById(id);
        var monthExpense = getAllMonthExpenses(expense.getDatetime());
        if(updateDto.description() != null)
            verifyDescription(updateDto.description(), monthExpense);
        expense.update(updateDto);
        return new ReadExpenseData(expense);
    }

    public void deleteExpense(Long id) {
        var expense = repository.getReferenceById(id);
        repository.delete(expense);
    }

    private List<Expense> getAllMonthExpenses(LocalDateTime dateTime) {
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
