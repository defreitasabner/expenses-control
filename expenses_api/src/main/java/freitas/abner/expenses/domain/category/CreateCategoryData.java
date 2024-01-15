package freitas.abner.expenses.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryData(
        @NotBlank
        @Size(min = 1, message = "Name should have at least 1 chars.")
        @Size(max = 100, message = "Name should have less than 100 chars.")
        String name
) {
}
