package com.jesussanchez.veterinaria.service;

import com.jesussanchez.veterinaria.entity.CitaMedica;
import com.jesussanchez.veterinaria.entity.ExpedienteClinico;
import com.jesussanchez.veterinaria.exception.ApiException;
import com.jesussanchez.veterinaria.repository.CitaRepository;
import com.jesussanchez.veterinaria.repository.ExpedienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final CitaRepository citaRepository;

    @Transactional
    public ExpedienteClinico registrarExpediente(Long citaId, String diagnostico, String tratamiento, Double pesoKg) {
        CitaMedica cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new ApiException("Cita médica no encontrada", HttpStatus.NOT_FOUND));

        if (cita.getEstado() == CitaMedica.EstadoCita.CANCELADA) {
            throw new ApiException("No se puede registrar un expediente para una cita cancelada", HttpStatus.BAD_REQUEST);
        }

        // Cambiar el estado de la cita a COMPLETADA
        cita.setEstado(CitaMedica.EstadoCita.COMPLETADA);
        citaRepository.save(cita);

        ExpedienteClinico expediente = new ExpedienteClinico();
        expediente.setCita(cita);
        expediente.setDiagnostico(diagnostico);
        expediente.setTratamiento(tratamiento);
        expediente.setPesoKg(pesoKg);
        expediente.setFechaRegistro(LocalDateTime.now());

        return expedienteRepository.save(expediente);
    }

    public List<ExpedienteClinico> obtenerHistorialPorMascota(Long mascotaId) {
        return expedienteRepository.findByCitaMascotaId(mascotaId);
    }
}