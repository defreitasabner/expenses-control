package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.user.AuthenticationData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping
    public ResponseEntity<Void> login(@RequestBody @Valid AuthenticationData authData) {
        var token = new UsernamePasswordAuthenticationToken(authData.username(), authData.password());
        var authentication = authManager.authenticate(token);
        return ResponseEntity.ok().build();
    }

}
