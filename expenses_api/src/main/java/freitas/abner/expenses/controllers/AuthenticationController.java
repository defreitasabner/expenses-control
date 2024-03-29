package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.user.AuthenticationData;
import freitas.abner.expenses.domain.user.User;
import freitas.abner.expenses.infrastructure.security.JwtTokenData;
import freitas.abner.expenses.infrastructure.security.TokenService;
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

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<JwtTokenData> login(@RequestBody @Valid AuthenticationData authData) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(authData.username(), authData.password());
        var authentication = authManager.authenticate(authenticationToken);
        var jwtToken = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new JwtTokenData(jwtToken));
    }

}
