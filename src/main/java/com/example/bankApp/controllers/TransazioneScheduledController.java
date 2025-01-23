package com.example.bankApp.controllers;

import com.example.bankApp.domain.dto.requests.TransazioneScheduledRequest;
import com.example.bankApp.domain.dto.requests.TransazioneScheduledUpdateRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.dto.responses.GenericResponse;
import com.example.bankApp.domain.exceptions.IllegalTransactionException;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.services.TransazioneScheduledService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/scheduled_transaction")
public class TransazioneScheduledController {

    @Autowired
    private TransazioneScheduledService transazioneScheduledService;

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> createTransazioneScheduled(@RequestBody TransazioneScheduledRequest request) throws IllegalTransactionException, MyEntityNotFoundException, SchedulerException {
        return new ResponseEntity<>(transazioneScheduledService.createTransazioneScheduled(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> updateTransazioneScheduled(@PathVariable Long id,
                                                                       @RequestBody TransazioneScheduledUpdateRequest request) throws SchedulerException {
        return new ResponseEntity<>(transazioneScheduledService.updateTransazioneScheduled(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) throws SchedulerException {
        transazioneScheduledService.deleteTransazioneScheduledById(id);
        return new ResponseEntity<>(new GenericResponse
                ("Transazione schedulata con id " + id + " eliminata con successo"), HttpStatus.OK);
    }

}
