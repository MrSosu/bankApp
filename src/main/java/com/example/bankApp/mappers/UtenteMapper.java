package com.example.bankApp.mappers;

import com.example.bankApp.domain.dto.requests.CreateUtenteRequest;
import com.example.bankApp.domain.dto.responses.UtenteProfiloResponse;
import com.example.bankApp.domain.entities.Utente;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.services.ComuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteMapper {

    @Autowired
    private ComuneService comuneService;

    public Utente fromCreateUtenteRequest(CreateUtenteRequest request) throws MyEntityNotFoundException {
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

    public UtenteProfiloResponse fromUtenteToProfilo(Utente utente) {
        return UtenteProfiloResponse.builder()
                .nome(utente.getNome())
                .cognome(utente.getCognome())
                .dataNascita(utente.getDataNascita())
                .indirizzo(utente.getIndirizzo())
                .codiceFiscale(utente.getCodiceFiscale())
                .email(utente.getEmail())
                .telefono(utente.getTelefono())
                .comune(utente.getComune().getNome())
                .build();
    }
}
