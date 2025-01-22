package com.example.bankApp.services;

import com.example.bankApp.domain.dto.requests.TransazioneRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.entities.Conto;
import com.example.bankApp.domain.entities.Transazione;
import com.example.bankApp.domain.entities.Utente;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.domain.exceptions.IllegalTransactionException;
import com.example.bankApp.repositories.TransazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransazioneService {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ContoService contoService;
    @Autowired
    private TransazioneRepository transazioneRepository;

    @Transactional
    public EntityIdResponse createTransazione(TransazioneRequest request) throws MyEntityNotFoundException, IllegalTransactionException {
        // Verifico che l'utente esista e lo prendo
        Utente utente = utenteService.getById(request.id_utente());
        // Verifico che i due conti esistano e li prendo
        Conto contoMittente = contoService.getById(request.id_mittente());
        Conto contoDestinatario = contoService.getById(request.id_destinatario());
        // Verifico che il conto mittente appartenga all'utente
        if (!contoMittente.getIntestatari().contains(utente)) {
            throw new IllegalTransactionException("Il conto " + contoMittente.getId() + " non appartiene all'utente " +
                    utente.getId());
        }
        // Verifico che il conto mittente abbia abbastanza cash
        if (contoMittente.getSaldo() < request.amount()) {
            throw new IllegalTransactionException("non hai abbastanza soldi sul conto!");
        }
        // Modifico il saldo nei conti
        contoService.updateSaldo(contoMittente.getId(), contoMittente.getSaldo() - request.amount());
        contoService.updateSaldo(contoDestinatario.getId(), contoDestinatario.getSaldo() + request.amount());
        Transazione transazione = transazioneRepository.save(Transazione
                .builder()
                .amount(request.amount())
                .contoMittente(contoMittente)
                .contoDestinatario(contoDestinatario)
                .timestamp(LocalDateTime.now())
                .build());
        return EntityIdResponse.builder().id(transazione.getId()).build();
    }
}
