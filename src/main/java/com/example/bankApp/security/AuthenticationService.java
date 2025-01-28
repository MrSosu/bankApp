package com.example.bankApp.security;

import com.example.bankApp.domain.dto.requests.AuthRequest;
import com.example.bankApp.domain.dto.requests.RegisterRequest;
import com.example.bankApp.domain.dto.responses.AuthenticationResponse;
import com.example.bankApp.domain.dto.responses.GenericResponse;
import com.example.bankApp.domain.entities.Utente;
import com.example.bankApp.services.ComuneService;
import com.example.bankApp.services.TokenBlackListService;
import com.example.bankApp.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ComuneService comuneService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenBlackListService tokenBlackListService;

    public AuthenticationResponse register(RegisterRequest request) {
        Utente utente = Utente
                .builder()
                .nome(request.nome())
                .cognome(request.cognome())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .telefono(request.telefono())
                .codiceFiscale(request.codiceFiscale())
                .dataNascita(request.dataNascita())
                .indirizzo(request.indirizzo())
                .comune(comuneService.getById(request.comune_id().id()))
                .build();
        String jwtToken = jwtService.generateToken(utente);
        utente.setRegistrationToken(jwtToken);
        utenteService.insertUtente(utente);
        // TODO invio email di conferma
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email().toLowerCase(),
                request.password()
        ));
        Utente utente = utenteService.getByEmail(request.email());
        String token = jwtService.generateToken(utente);
        utente.setLastLogin(LocalDateTime.now());
        utenteService.insertUtente(utente);
        return AuthenticationResponse.builder().token(token).build();
    }

    public GenericResponse logout(Long idUtente, String token) {
        tokenBlackListService.insertToken(idUtente,token);
        return GenericResponse.builder().message("Logout effettuato con successo").build();
    }
}
