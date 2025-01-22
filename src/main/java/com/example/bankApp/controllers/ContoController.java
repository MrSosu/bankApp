package com.example.bankApp.controllers;

import com.example.bankApp.domain.dto.requests.CreateContoRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.entities.Conto;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.services.ContoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/conto")
public class ContoController {

    @Autowired
    private ContoService contoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Conto> getContoById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(contoService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> createConto(@RequestBody @Valid CreateContoRequest request) {
        return new ResponseEntity<>(contoService.create(request), HttpStatus.CREATED);
    }

}
