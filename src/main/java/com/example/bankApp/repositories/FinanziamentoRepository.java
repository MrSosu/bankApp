package com.example.bankApp.repositories;

import com.example.bankApp.domain.entities.Finanziamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanziamentoRepository extends JpaRepository<Finanziamento, Long> {
}
