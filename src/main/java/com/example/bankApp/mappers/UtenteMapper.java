package com.example.bankApp.mappers;

import com.example.bankApp.domain.dto.requests.CreateUtenteRequest;
import com.example.bankApp.domain.entities.Utente;
import com.example.bankApp.domain.exceptions.EntityNotFoundException;
import com.example.bankApp.services.ComuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteMapper {

    @Autowired
    private ComuneService comuneService;

    public Utente fromCreateUtenteRequest(CreateUtenteRequest request) throws EntityNotFoundException {
        return Utente
                .builder()
                .nome(request.nome())
                .cognome(request.cognome())
                .email(request.email())
                .indirizzo(request.indirizzo())
                .codiceFiscale(request.codiceFiscale())
                .comune(comuneService.getById(request.comune_id().id()))
                .dataNascita(request.dataNascita())
                .telefono(request.telefono())
                .build();
    }

}
