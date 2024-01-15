package freitas.abner.expenses.domain.expense;

import freitas.abner.expenses.domain.category.CategoryService;
import freitas.abner.expenses.domain.expense.validators.ExpenseValidator;
import freitas.abner.expenses.domain.user.User;
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

    @Autowired
    List<ExpenseValidator> validators;

    public Expense registerNewExpense(CreateExpenseData expenseDto, User user)
    {
        validators.forEach(validator -> validator.validate(expenseDto, user));
        var category = categoryService.getCategoryById(expenseDto.categoryId());
        var expense = new Expense(expenseDto, category, user);
        repository.save(expense);
        return expense;
    }

    public Page<ReadExpenseData> getAllExpenseDataPageable(Pageable pageable, User user) {
        return repository.findAllByUserId(user.getId(), pageable).map(ReadExpenseData::new);
    }

    public ReadExpenseData getExpenseDetail(Long id, User user) {
        var expense = repository.getReferenceById(id);
        if(!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You couldn't check another user expenses.");
        }
        return new ReadExpenseData(expense);
    }

    public ReadExpenseData updateExpense(UpdateExpenseData updateDto, User user) throws SameDescriptionException {
        var expense = repository.findByDescriptionAndUserId(updateDto.description(), user.getId());
        if(expense == null) {
            throw new RuntimeException("Expense not found.");
        }
        var monthExpense = getAllMonthExpenses(expense.getDatetime(), expense.getUser());
        if(updateDto.description() != null)
            verifyDescription(updateDto.description(), monthExpense);
        expense.update(updateDto);
        return new ReadExpenseData(expense);
    }

    public void deleteExpense(DeleteExpenseData deleteExpenseData, User user) {
        var expense = repository.findByDescriptionAndUserId(
                deleteExpenseData.description(),
                user.getId()
        );
        if (expense == null) {
            throw new RuntimeException("Expense not found.");
        }
        repository.delete(expense);
    }

    private List<Expense> getAllMonthExpenses(LocalDateTime dateTime, User user) {
        var firstDay = LocalDateTime.of(
                LocalDate.of(
                        dateTime.getYear(),
                        dateTime.getMonth(),
                        1),
                LocalTime.of(0, 0, 0)
        );
        var lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        return repository.findAllByDatetimeBetweenAndUserId(firstDay, lastDay, user.getId());
    }

    private void verifyDescription(String description, List<Expense> monthExpenses) throws SameDescriptionException {
        if(monthExpenses.stream().anyMatch(monthExpense -> monthExpense.getDescription().equals(description)))
            throw new SameDescriptionException("");
    }
}
