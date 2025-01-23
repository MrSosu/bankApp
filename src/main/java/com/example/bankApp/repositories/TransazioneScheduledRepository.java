package com.example.bankApp.repositories;

import com.example.bankApp.domain.entities.TransazioneScheduled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransazioneScheduledRepository extends JpaRepository<TransazioneScheduled, Long> {
}
