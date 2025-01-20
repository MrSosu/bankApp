package com.example.bankApp.repositories;

import com.example.bankApp.domain.entities.Transazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, Long> {
}
