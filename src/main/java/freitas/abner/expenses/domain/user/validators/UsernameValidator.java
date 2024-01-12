package freitas.abner.expenses.domain.user.validators;

import freitas.abner.expenses.domain.user.RegisterData;
import freitas.abner.expenses.domain.user.UserRepository;
import freitas.abner.expenses.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator implements RegisterValidator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(RegisterData registerData) {
        var userAlreadyExists = userRepository.findByUsername(registerData.username());
        if(userAlreadyExists != null) {
            throw new ValidationException("Username is already been used.");
        }
    }
}
