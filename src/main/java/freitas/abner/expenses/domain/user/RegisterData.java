package freitas.abner.expenses.domain.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record RegisterData(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank @JsonAlias("re_password")
        String rePassword
) {
}
