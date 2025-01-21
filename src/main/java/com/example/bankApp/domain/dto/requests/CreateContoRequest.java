package com.example.bankApp.domain.dto.requests;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateContoRequest(
        @NotEmpty
        List<Long> intestatari_id,
        @Positive
        Double costo
) {
}
