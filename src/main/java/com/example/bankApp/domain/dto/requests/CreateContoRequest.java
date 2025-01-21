package com.example.bankApp.domain.dto.requests;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateContoRequest(
        @NotEmpty
        List<EntityIdRequest> intestatari_id,
        @Positive
        Double costo
) {
}
