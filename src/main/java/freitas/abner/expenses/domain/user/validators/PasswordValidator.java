package freitas.abner.expenses.domain.user.validators;

import freitas.abner.expenses.domain.user.RegisterData;
import freitas.abner.expenses.exceptions.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements RegisterValidator{

    @Override
    public void validate(RegisterData registerData) {
        if(!registerData.password().equals(registerData.rePassword())) {
            throw new ValidationException("Password and Re-Password didn't match.");
        }
    }
}
