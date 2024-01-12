package freitas.abner.expenses.domain.category;

import freitas.abner.expenses.domain.category.Category;
import freitas.abner.expenses.domain.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category getCategoryById(Long id) {
        return repository.getReferenceById(id);
    }

}
