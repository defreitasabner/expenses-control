package freitas.abner.expenses.domain.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterData(
        @NotBlank
        @Size(min = 3, message = "Username should have at least 3 chars.")
        @Size(max = 100, message = "Username should have less than 100 chars.")
        String username,
        @NotBlank
        @Email(message = "Email not valid.", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,
        @NotBlank
        @Size(min = 8, message = "Password should have at least 8 chars.")
        @Size(max = 255, message = "Password should have less than 255 chars.")
        String password,
        @NotBlank
        @JsonAlias("re_password")
        String rePassword
) {
}
