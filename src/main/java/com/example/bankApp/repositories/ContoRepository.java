package com.example.bankApp.repositories;

import com.example.bankApp.domain.entities.Conto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContoRepository extends JpaRepository<Conto, Long> {
}
