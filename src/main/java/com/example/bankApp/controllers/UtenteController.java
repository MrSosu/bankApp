package com.example.bankApp.controllers;

import com.example.bankApp.domain.dto.requests.CreateUtenteRequest;
import com.example.bankApp.domain.dto.requests.UpdateUtenteRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.dto.responses.GenericResponse;
import com.example.bankApp.domain.dto.responses.UtenteProfiloResponse;
import com.example.bankApp.domain.entities.Utente;
import com.example.bankApp.domain.exceptions.EntityNotFoundException;
import com.example.bankApp.services.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/v1/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Utente> getById(@PathVariable Long id) throws EntityNotFoundException {
        return new ResponseEntity<>(utenteService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<UtenteProfiloResponse> getProfilo(@PathVariable Long id) throws EntityNotFoundException {
        return new ResponseEntity<>(utenteService.getProfilo(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Utente>> getAll() {
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> create(@RequestBody @Valid CreateUtenteRequest request) throws EntityNotFoundException {
        return new ResponseEntity<>(utenteService.createUtente(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateUtenteRequest request) throws EntityNotFoundException {
        return new ResponseEntity<>(utenteService.updateUtente(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) {
        utenteService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("Utente con id " + id + " eliminato correttamente"), HttpStatus.OK);
    }

}
