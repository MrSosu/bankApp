package com.example.bankApp.domain.dto.requests;

import com.example.bankApp.domain.entities.Comune;

import java.time.LocalDate;

public record UpdateUtenteRequest(
        String nome,
        String cognome,
        String telefono,
        String indirizzo,
        LocalDate dataNascita,
        Comune comune
) {
}
