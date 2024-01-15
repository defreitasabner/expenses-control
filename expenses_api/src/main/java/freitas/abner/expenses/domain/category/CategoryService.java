package freitas.abner.expenses.domain.category;

import freitas.abner.expenses.domain.category.dtos.CreateOrUpdateCategoryData;
import freitas.abner.expenses.domain.category.dtos.DeleteCategoryData;
import freitas.abner.expenses.domain.category.dtos.ReadCategoryData;
import freitas.abner.expenses.domain.category.validators.CategoryValidator;
import freitas.abner.expenses.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Autowired
    List<CategoryValidator> validators;

    public Category getCategoryById(Long id) {
        return repository.getReferenceById(id);
    }

    public Category createNewCategory(CreateOrUpdateCategoryData categoryData, User user) {
        validators.forEach(validator -> validator.validate(categoryData, user));
        var newCategory = new Category(categoryData, user);
        repository.save(newCategory);
        return newCategory;
    }

    public Page<ReadCategoryData> getAllCategoriesByUser(Pageable pageable, User user) {
        return repository.findAllByUserId(user.getId(), pageable).map(ReadCategoryData::new);
    }

    public Category update(CreateOrUpdateCategoryData updateCategoryData, User user) {
        validators.forEach(validator -> validator.validate(updateCategoryData, user));
        var category = repository.findByNameAndUserId(updateCategoryData.name(), user.getId());
        if(category == null) {
            throw new EntityNotFoundException();
        }
        category.update(updateCategoryData);
        return category;
    }

    public void delete(DeleteCategoryData deleteCategoryData, User user) {
        var category = repository.findByNameAndUserId(deleteCategoryData.name(), user.getId());
        if(category == null) {
            throw new EntityNotFoundException();
        }
        repository.delete(category);
    }
}
