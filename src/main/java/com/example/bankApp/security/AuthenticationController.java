package com.example.bankApp.security;

import com.example.bankApp.domain.dto.requests.AuthRequest;
import com.example.bankApp.domain.dto.requests.RegisterRequest;
import com.example.bankApp.domain.dto.responses.AuthenticationResponse;
import com.example.bankApp.domain.dto.responses.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.CREATED);
    }

    @PostMapping("/logout/{id_utente}")
    public ResponseEntity<GenericResponse> logout(@PathVariable Long id_utente, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return new ResponseEntity<>(authenticationService.logout(id_utente, token), HttpStatus.CREATED);
    }

}
