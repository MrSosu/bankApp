package com.example.bankApp.domain.dto.responses;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String exception,
        String message
) {
}
