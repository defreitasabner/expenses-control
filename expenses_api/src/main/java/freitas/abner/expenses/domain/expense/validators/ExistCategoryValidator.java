package freitas.abner.expenses.domain.expense.validators;

import freitas.abner.expenses.domain.category.CategoryRepository;
import freitas.abner.expenses.domain.category.CategoryService;
import freitas.abner.expenses.domain.expense.CreateExpenseData;
import freitas.abner.expenses.domain.user.User;
import freitas.abner.expenses.exceptions.InvalidCategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistCategoryValidator implements ExpenseValidator {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void validate(CreateExpenseData expenseData, User user) {
        var category = categoryService.getCategoryById(expenseData.categoryId());
        if(category == null) {
            throw new InvalidCategoryException("Category do not exists.");
        }
    }
}
