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

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transazione")
public class Transazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Check(constraints = "amount > 0", name = "check_amount_positive")
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conto_mittente")
    private Conto contoMittente;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conto_destinatario")
    private Conto contoDestinatario;
    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id")
    private Utente utente;

}
