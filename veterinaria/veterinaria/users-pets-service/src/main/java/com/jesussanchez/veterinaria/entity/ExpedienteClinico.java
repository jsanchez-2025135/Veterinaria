package com.jesussanchez.veterinaria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "expedientes_clinicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", nullable = false, unique = true)
    private CitaMedica cita;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String diagnostico;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String tratamiento;

    private Double pesoKg;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}