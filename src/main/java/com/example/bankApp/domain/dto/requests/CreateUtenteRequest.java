package com.example.bankApp.domain.dto.requests;

import com.example.bankApp.domain.entities.Comune;

import java.time.LocalDate;

public record CreateUtenteRequest(
        String nome,
        String cognome,
        String email,
        String telefono,
        String codiceFiscale,
        String indirizzo,
        LocalDate dataNascita,
        Comune comune
) {
}
