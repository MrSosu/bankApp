package com.example.bankApp.domain.dto.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransazioneScheduledUpdateRequest(
        @Positive
        Double amount,
        @Future
        LocalDateTime publishTime
) {
}
