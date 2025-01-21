package com.example.bankApp.services;

import com.example.bankApp.domain.entities.Comune;
import com.example.bankApp.domain.exceptions.EntityNotFoundException;
import com.example.bankApp.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComuneService {

    @Autowired
    private ComuneRepository comuneRepository;

    public Comune getById(Long id) throws EntityNotFoundException {
        return comuneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comune non trovato"));
    }

}
