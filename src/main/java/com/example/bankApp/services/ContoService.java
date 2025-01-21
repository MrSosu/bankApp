package com.example.bankApp.services;

import com.example.bankApp.domain.dto.requests.CreateContoRequest;
import com.example.bankApp.domain.dto.responses.EntityIdResponse;
import com.example.bankApp.domain.entities.Conto;
import com.example.bankApp.domain.entities.Utente;
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

    public EntityIdResponse create(CreateContoRequest request) {
        Conto conto = contoMapper.fromContoRequest(request);
        return EntityIdResponse
                .builder()
                .id(contoRepository.save(conto).getId())
                .build();
    }
}
