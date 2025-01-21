package com.example.bankApp.domain.dto.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UtenteProfiloResponse (
        String nome,
        String cognome,
        LocalDate dataNascita,
        String email,
        String telefono,
        String codiceFiscale,
        String indirizzo,
        String comune
){
}
