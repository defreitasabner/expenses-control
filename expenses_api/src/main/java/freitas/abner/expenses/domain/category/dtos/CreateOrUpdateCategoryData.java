package freitas.abner.expenses.domain.category.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrUpdateCategoryData(
        @NotBlank
        @Size(min = 1, message = "Name should have at least 1 chars.")
        @Size(max = 100, message = "Name should have less than 100 chars.")
        String name
) {
}
