package com.example.bankApp.controllers;

import com.example.bankApp.domain.dto.requests.TransazioneRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.domain.exceptions.IllegalTransactionException;
import com.example.bankApp.services.TransazioneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/transaction")
public class TransazioneController {

    @Autowired
    private TransazioneService transazioneService;

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> createTransazione(@RequestBody @Valid TransazioneRequest request) throws IllegalTransactionException, MyEntityNotFoundException {
        return new ResponseEntity<>(transazioneService.createTransazione(request), HttpStatus.CREATED);
    }

}
