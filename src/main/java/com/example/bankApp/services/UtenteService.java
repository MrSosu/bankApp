package com.example.bankApp.services;

import com.example.bankApp.domain.dto.requests.CreateUtenteRequest;
import com.example.bankApp.domain.dto.requests.UpdateUtenteRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.entities.Utente;
import com.example.bankApp.domain.exceptions.EntityNotFoundException;
import com.example.bankApp.mappers.UtenteMapper;
import com.example.bankApp.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private UtenteMapper utenteMapper;
    @Autowired
    private ComuneService comuneService;

    public Utente getById(Long id) throws EntityNotFoundException {
        return utenteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("utente con id " + id + " non trovato"));
    }

    public List<Utente> getAll() {
        return utenteRepository.findAll();
    }

    public EntityIdResponse createUtente(CreateUtenteRequest request) throws EntityNotFoundException {
        Utente utenteSaved = utenteRepository.save(utenteMapper.fromCreateUtenteRequest(request));
        return new EntityIdResponse(utenteSaved.getId());
    }

    public EntityIdResponse updateUtente(Long id, UpdateUtenteRequest request) throws EntityNotFoundException {
        Utente myUtente = getById(id);
        if (request.nome() != null) myUtente.setNome(request.nome());
        if (request.cognome()!= null) myUtente.setCognome(request.cognome());
        if (request.indirizzo() != null) myUtente.setIndirizzo(request.indirizzo());
        if (request.dataNascita() != null) myUtente.setDataNascita(request.dataNascita());
        if (request.comune_id() != null) {
            myUtente.setComune(comuneService.getById(request.comune_id().id()));
        }
        return new EntityIdResponse(utenteRepository.save(myUtente).getId());
    }

    public void deleteById(Long id) {
        utenteRepository.deleteById(id);
    }

}
