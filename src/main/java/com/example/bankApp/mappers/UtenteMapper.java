package com.example.bankApp.mappers;

import com.example.bankApp.domain.dto.requests.CreateUtenteRequest;
import com.example.bankApp.domain.entities.Utente;
import org.springframework.stereotype.Service;

@Service
public class UtenteMapper {

    public Utente fromCreateUtenteRequest(CreateUtenteRequest request) {
        return Utente
                .builder()
                .nome(request.nome())
                .cognome(request.cognome())
                .email(request.email())
                .indirizzo(request.indirizzo())
                .codiceFiscale(request.codiceFiscale())
                .comune(request.comune())
                .dataNascita(request.dataNascita())
                .telefono(request.telefono())
                .build();
    }

}
