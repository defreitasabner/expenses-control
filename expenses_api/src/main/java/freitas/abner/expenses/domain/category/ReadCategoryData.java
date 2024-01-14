package freitas.abner.expenses.domain.category;

public record ReadCategoryData(
        Long id,
        String name
) {
    public ReadCategoryData(Category category) {
        this(category.getId(), category.getName());
    }
}
