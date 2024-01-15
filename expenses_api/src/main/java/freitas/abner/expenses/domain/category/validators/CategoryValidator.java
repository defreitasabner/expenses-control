package freitas.abner.expenses.domain.category.validators;

import freitas.abner.expenses.domain.category.dtos.CreateOrUpdateCategoryData;
import freitas.abner.expenses.domain.user.User;

public interface CategoryValidator {
    void validate(CreateOrUpdateCategoryData createOrUpdateCategoryData, User user);
}
