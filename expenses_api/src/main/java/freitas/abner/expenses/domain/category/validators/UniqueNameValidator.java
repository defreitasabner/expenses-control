package freitas.abner.expenses.domain.category.validators;

import freitas.abner.expenses.domain.category.CategoryRepository;
import freitas.abner.expenses.domain.category.dtos.CreateOrUpdateCategoryData;
import freitas.abner.expenses.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueNameValidator implements CategoryValidator {

    @Autowired
    private CategoryRepository repository;

    @Override
    public void validate(CreateOrUpdateCategoryData createOrUpdateCategoryData, User user) {
        var categoryAlreadyExists = repository.existsByNameAndUserId(
                createOrUpdateCategoryData.name(),
                user.getId()
        );
        if(categoryAlreadyExists) {
            throw new RuntimeException("This category already exists.");
        }
    }
}
