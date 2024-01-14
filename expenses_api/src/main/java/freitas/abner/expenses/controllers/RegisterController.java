package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.user.RegisterData;
import freitas.abner.expenses.domain.user.RegisterService;
import freitas.abner.expenses.domain.user.UserDetailData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid RegisterData registerData)
    {
        var user = registerService.registerNewUser(registerData);
        return ResponseEntity.ok().body(new UserDetailData(user));
    }

}
