package freitas.abner.expenses.domain.category.dtos;

import freitas.abner.expenses.domain.category.Category;

public record ReadCategoryData(
        Long id,
        String name
) {
    public ReadCategoryData(Category category) {
        this(category.getId(), category.getName());
    }
}
