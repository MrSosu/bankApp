package com.example.bankApp.services;

import com.example.bankApp.domain.dto.requests.CreateContoRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.entities.Conto;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.mappers.ContoMapper;
import com.example.bankApp.repositories.ContoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContoService {

    @Autowired
    private ContoRepository contoRepository;
    @Autowired
    private ContoMapper contoMapper;

    public Conto getById(Long id) throws MyEntityNotFoundException {
        return contoRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("il conto con id " + id + " non esiste!"));
    }

    public EntityIdResponse create(CreateContoRequest request) {
        Conto conto = contoMapper.fromContoRequest(request);
        return EntityIdResponse
                .builder()
                .id(contoRepository.save(conto).getId())
                .build();
    }

    public Conto updateSaldo(Long id, Double newSaldo) throws MyEntityNotFoundException {
        Conto conto = getById(id);
        conto.setSaldo(newSaldo);
        return contoRepository.save(conto);
    }

}
