package com.example.bankApp.controllers;

import com.example.bankApp.domain.dto.requests.CreateContoRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.services.ContoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/conto")
public class ContoController {

    @Autowired
    private ContoService contoService;

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> createConto(@RequestBody @Valid CreateContoRequest request) {
        return new ResponseEntity<>(contoService.create(request), HttpStatus.CREATED);
    }

}
