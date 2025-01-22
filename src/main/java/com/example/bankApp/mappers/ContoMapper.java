package com.example.bankApp.mappers;

import com.example.bankApp.domain.dto.requests.CreateContoRequest;
import com.example.bankApp.domain.dto.responses.ContoResponse;
import com.example.bankApp.domain.entities.Conto;
import com.example.bankApp.domain.entities.Utente;
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

    public ContoResponse toContoResponse(Conto conto) {
        return ContoResponse
                .builder()
                .id(conto.getId())
                .saldo(conto.getSaldo())
                .costo(conto.getCosto())
                .dataSottoscrizione(conto.getDataSottoscrizione())
                .idIntestatari(conto.getIntestatari().stream().map(Utente::getId).toList())
                .build();
    }

}
