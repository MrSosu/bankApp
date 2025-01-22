package com.example.bankApp.controllers;

import com.example.bankApp.domain.dto.requests.TransazioneRequest;
import com.example.bankApp.domain.dto.responses.TransazioneResponse;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.domain.exceptions.IllegalTransactionException;
import com.example.bankApp.services.TransazioneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/v1/transaction")
public class TransazioneController {

    @Autowired
    private TransazioneService transazioneService;

    @GetMapping("/all")
    public ResponseEntity<List<TransazioneResponse>> getAll() {
        return new ResponseEntity<>(transazioneService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> createTransazione(@RequestBody @Valid TransazioneRequest request) throws IllegalTransactionException, MyEntityNotFoundException {
        if (request.tipoOperazione().equals("transazione")) {
            return new ResponseEntity<>(transazioneService.createTransazione(request), HttpStatus.CREATED);
        } else if (request.tipoOperazione().equals("versamento")) {
            return new ResponseEntity<>(transazioneService.createVersamento(request), HttpStatus.CREATED);
        } else if (request.tipoOperazione().equals("prelievo")) {
            return new ResponseEntity<>(transazioneService.createPrelievo(request), HttpStatus.CREATED);
        } else throw new IllegalArgumentException("Tipo di operazione sul conto non valida");
    }

}
