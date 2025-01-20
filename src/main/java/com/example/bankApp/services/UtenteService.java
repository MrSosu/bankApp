package com.example.bankApp.services;

import com.example.bankApp.domain.dto.requests.CreateUtenteRequest;
import com.example.bankApp.domain.dto.requests.UpdateUtenteRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.entities.Utente;
import com.example.bankApp.domain.exceptions.UtenteNotFoundException;
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

    public Utente getById(Long id) throws UtenteNotFoundException {
        return utenteRepository
                .findById(id)
                .orElseThrow(() -> new UtenteNotFoundException("utente con id " + id + " non trovato"));
    }

    public List<Utente> getAll() {
        return utenteRepository.findAll();
    }

    public EntityIdResponse createUtente(CreateUtenteRequest request) {
        Utente utenteSaved = utenteRepository.save(utenteMapper.fromCreateUtenteRequest(request));
        return new EntityIdResponse(utenteSaved.getId());
    }

    public EntityIdResponse updateUtente(Long id, UpdateUtenteRequest request) throws UtenteNotFoundException {
        Utente myUtente = getById(id);
        myUtente.setNome(request.nome());
        myUtente.setCognome(request.cognome());
        myUtente.setIndirizzo(request.indirizzo());
        myUtente.setDataNascita(request.dataNascita());
        myUtente.setComune(request.comune());
        return new EntityIdResponse(utenteRepository.save(myUtente).getId());
    }

    public void deleteById(Long id) {
        utenteRepository.deleteById(id);
    }

}
