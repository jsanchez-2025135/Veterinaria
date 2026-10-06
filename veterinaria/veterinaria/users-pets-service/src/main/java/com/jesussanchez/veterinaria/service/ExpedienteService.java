package com.jesussanchez.veterinaria.service;

import com.jesussanchez.expediente.client.CitaClient;
import com.jesussanchez.expediente.entity.ExpedienteClinico;
import com.jesussanchez.expediente.repository.ExpedienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final CitaClient citaClient;

    @Transactional
    public ExpedienteClinico crearExpediente(ExpedienteClinico expediente) {
        // Validar que no exista expediente para esta cita
        if (expedienteRepository.existsByCitaId(expediente.getCitaId())) {
            throw new RuntimeException("La cita ya posee un expediente clínico registrado.");
        }

        expediente.setFechaRegistro(LocalDateTime.now());
        ExpedienteClinico guardado = expedienteRepository.save(expediente);

        // Regla 8.4: Notificar a cita-service para actualizar estado a COMPLETADA
        citaClient.actualizarEstadoCita(expediente.getCitaId(), "COMPLETADA");

        return guardado;
    }
}