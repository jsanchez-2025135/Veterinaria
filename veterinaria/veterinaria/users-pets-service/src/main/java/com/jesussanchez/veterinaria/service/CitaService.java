package com.jesussanchez.veterinaria.service;

import com.jesussanchez.veterinaria.entity.CitaMedica;
import com.jesussanchez.veterinaria.entity.Mascota;
import com.jesussanchez.veterinaria.entity.Usuario;
import com.jesussanchez.veterinaria.exception.ApiException;
import com.jesussanchez.veterinaria.repository.CitaRepository;
import com.jesussanchez.veterinaria.repository.MascotaRepository;
import com.jesussanchez.veterinaria.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CitaMedica agendarCita(Long mascotaId, Long veterinarioId, LocalDateTime fechaHora, String motivo, String emailClienteActual) {
        LocalDateTime inicioNueva = fechaHora;
        LocalDateTime finNueva = fechaHora.plusMinutes(30);

        // 1. Validar disponibilidad del veterinario (cruce de horarios de 30 mins)
        List<CitaMedica> citasVeterinario = citaRepository.findByVeterinarioIdAndEstadoNot(veterinarioId, CitaMedica.EstadoCita.CANCELADA);
        for (CitaMedica cita : citasVeterinario) {
            LocalDateTime inicioExistente = cita.getFechaHora();
            LocalDateTime finExistente = inicioExistente.plusMinutes(30);

            if (inicioNueva.isBefore(finExistente) && finNueva.isAfter(inicioExistente)) {
                throw new ApiException("El veterinario ya cuenta con una cita en ese rango de horario.", HttpStatus.BAD_REQUEST);
            }
        }

        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new ApiException("Mascota no encontrada", HttpStatus.NOT_FOUND));

        Usuario clienteActual = usuarioRepository.findByEmail(emailClienteActual)
                .orElseThrow(() -> new ApiException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if (clienteActual.getRol().name().equals("CLIENTE") && !mascota.getCliente().getId().equals(clienteActual.getId())) {
            throw new ApiException("No puedes agendar citas para mascotas que no te pertenecen.", HttpStatus.FORBIDDEN);
        }

        // 2. Límite de Citas Activas: Máximo 2 citas PENDIENTES el mismo día por CLIENTE
        LocalDateTime inicioDia = fechaHora.toLocalDate().atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1).minusNanos(1);

        List<CitaMedica> citasDelDiaCliente = citaRepository.findByMascotaClienteIdAndEstadoAndFechaHoraBetween(
                mascota.getCliente().getId(), CitaMedica.EstadoCita.PENDIENTE, inicioDia, finDia);

        if (citasDelDiaCliente.size() >= 2) {
            throw new ApiException("El cliente ya cuenta con el límite de 2 citas pendientes para el mismo día.", HttpStatus.BAD_REQUEST);
        }

        Usuario veterinario = usuarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new ApiException("Veterinario no encontrado", HttpStatus.NOT_FOUND));

        CitaMedica nuevaCita = new CitaMedica();
        nuevaCita.setMascota(mascota);
        nuevaCita.setVeterinario(veterinario);
        nuevaCita.setFechaHora(fechaHora);
        nuevaCita.setMotivo(motivo);
        nuevaCita.setEstado(CitaMedica.EstadoCita.PENDIENTE);

        return citaRepository.save(nuevaCita);
    }

    @Transactional
    public void cancelarCita(Long citaId, String emailUsuario) {
        CitaMedica cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new ApiException("Cita no encontrada", HttpStatus.NOT_FOUND));

        // 3. Cancelación con Anticipación: Faltan más de 2 horas
        LocalDateTime ahora = LocalDateTime.now();
        if (ahora.plusHours(2).isAfter(cita.getFechaHora())) {
            throw new ApiException("La cita solo puede ser cancelada con al menos 2 horas de anticipación.", HttpStatus.BAD_REQUEST);
        }

        cita.setEstado(CitaMedica.EstadoCita.CANCELADA);
        citaRepository.save(cita);
    }
}