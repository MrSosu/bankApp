package com.example.bankApp.domain.dto.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransazioneScheduledRequest(
        @Positive
        Double amount,
        @NotNull
        Long id_mittente,
        @NotNull
        Long id_destinatario,
        @NotNull
        Long id_utente,
        @NotNull
        @Future
        LocalDateTime publishTime
) {
}
