package com.example.bankApp.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "finanziamento")
public class Finanziamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double amount;
    @Check(constraints = "tasso >= 0")
    @Column(nullable = false)
    private Double tasso;
    @Check(constraints = "durata_mesi > 0")
    @Column(nullable = false, name = "durata_mesi")
    private Integer durataMesi;
    @Column(nullable = false, name = "data_inizio")
    private LocalDate dataInizio;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conto_id")
    private Conto conto;
    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id")
    private Utente utente;


}
