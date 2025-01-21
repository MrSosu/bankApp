package com.example.bankApp.domain.dto.requests;

import com.example.bankApp.domain.entities.Comune;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UpdateUtenteRequest(
        String nome,
        String cognome,
        @Nullable
        @Pattern(
                regexp = "^\\+?[0-9]{1,3}?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$\n",
                message = "Telefono non valido")
        String telefono,
        String indirizzo,
        @Nullable
        @Past(message = "La data di nascita non può essere nel passato")
        LocalDate dataNascita,
        EntityIdRequest comune_id
) {
}
