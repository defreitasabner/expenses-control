package freitas.abner.expenses.domain.category;

import freitas.abner.expenses.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category getCategoryById(Long id) {
        return repository.getReferenceById(id);
    }

    public Category createNewCategory(CreateCategoryData categoryData, User user) {
        var categoryAlreadyExists = repository.existsByNameAndUserId(
                categoryData.name(),
                user.getId()
        );
        if(categoryAlreadyExists) {
            throw new RuntimeException("This category already exists.");
        }
        var newCategory = new Category(categoryData, user);
        repository.save(newCategory);
        return newCategory;
    }

    public Page<ReadCategoryData> getAllCategoriesByUser(Pageable pageable, User user) {
        return repository.findAllByUserId(user.getId(), pageable).map(ReadCategoryData::new);
    }
}
