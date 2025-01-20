package com.example.bankApp.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "conto")
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Check(constraints = "costo >= 0", name = "costo_conto_positive")
    @Column(nullable = false)
    private Double costo;
    @Column(nullable = false)
    private Double saldo;
    @Column(nullable = false, name = "data_sottoscrizione")
    private LocalDate dataSottoscrizione;
    @ManyToMany(mappedBy = "conti")
    private Set<Utente> intestatari;

}
