package freitas.abner.expenses.domain.user;

import freitas.abner.expenses.domain.user.validators.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<RegisterValidator> validators;

    public User registerNewUser(RegisterData registerData) {
        validators.forEach(validator -> validator.validate(registerData));
        var encodedPassword = new BCryptPasswordEncoder().encode(registerData.password());
        var newUser = new User(registerData.username(), registerData.email(), encodedPassword);
        userRepository.save(newUser);
        return newUser;
    }
}
