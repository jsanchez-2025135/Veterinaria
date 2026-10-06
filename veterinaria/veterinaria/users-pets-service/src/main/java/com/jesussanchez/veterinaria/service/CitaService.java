package com.jesussanchez.veterinaria.service;

import com.jesussanchez.cita.entity.CitaMedica;
import com.jesussanchez.cita.entity.EstadoCita;
import com.jesussanchez.cita.repository.CitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaMedica agendarCita(CitaMedica cita) {
        LocalDateTime inicio = cita.getFechaHora();
        LocalDateTime fin = inicio.plusMinutes(30);

        // Regla 8.1: Validar traslape de horario del veterinario (30 min)
        boolean ocupado = citaRepository.existsByVeterinarioIdAndFechaHoraBetween(
                cita.getVeterinarioId(), inicio, fin.minusSeconds(1)
        );
        if (ocupado) {
            throw new RuntimeException("El veterinario no está disponible en este horario.");
        }

        // Regla 8.2: Validar límite de 2 citas pendientes para el cliente en el mismo día
        LocalDateTime inicioDia = inicio.toLocalDate().atStartOfDay();
        LocalDateTime finDia = inicio.toLocalDate().atTime(23, 59, 59);
        long citasPendientes = citaRepository.countByClienteIdAndEstadoAndFechaHoraBetween(
                cita.getClienteId(), EstadoCita.PENDIENTE, inicioDia, finDia
        );
        if (citasPendientes >= 2) {
            throw new RuntimeException("El cliente ya posee 2 citas pendientes para este día.");
        }

        cita.setEstado(EstadoCita.PENDIENTE);
        return citaRepository.save(cita);
    }

    public void cancelarCita(Long citaId) {
        CitaMedica cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // Regla 8.3: Cancelación con más de 2 horas de anticipación
        if (Duration.between(LocalDateTime.now(), cita.getFechaHora()).toHours() < 2) {
            throw new RuntimeException("La cita solo puede cancelarse con más de 2 horas de anticipación.");
        }

        cita.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(cita);
    }
}