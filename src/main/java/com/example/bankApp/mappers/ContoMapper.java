package com.example.bankApp.mappers;

import com.example.bankApp.domain.dto.requests.CreateContoRequest;
import com.example.bankApp.domain.entities.Conto;
import com.example.bankApp.domain.exceptions.MyEntityNotFoundException;
import com.example.bankApp.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class ContoMapper {

    @Autowired
    private UtenteService utenteService;

    public Conto fromContoRequest(CreateContoRequest request) {
        return Conto
                .builder()
                .costo(request.costo())
                .intestatari(request.intestatari_id().stream().map(id -> {
                    try {
                        return utenteService.getById(id);
                    } catch (MyEntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet()))
                .saldo(0d)
                .dataSottoscrizione(LocalDate.now())
                .build();
    }

}
