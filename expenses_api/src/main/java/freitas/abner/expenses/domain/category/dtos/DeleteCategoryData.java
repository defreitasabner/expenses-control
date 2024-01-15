package freitas.abner.expenses.domain.category.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeleteCategoryData(
        @NotBlank
        String name
) {
}
