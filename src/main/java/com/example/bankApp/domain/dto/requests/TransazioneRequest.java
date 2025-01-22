package com.example.bankApp.domain.dto.requests;

import com.example.bankApp.domain.enums.TipoOperazione;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransazioneRequest(
        @Positive
        Double amount,
        Long id_mittente,
        @NotNull
        Long id_destinatario,
        @NotNull
        Long id_utente,
        @NotNull
        String tipoOperazione
) {
}
