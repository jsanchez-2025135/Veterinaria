package com.jesussanchez.veterinaria.response;

import com.jesussanchez.veterinaria.entity.CitaMedica;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaResponse {
    private Long id;
    private Long mascotaId;
    private String nombreMascota;
    private Long veterinarioId;
    private String nombreVeterinario;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;

    public static CitaResponse fromEntity(CitaMedica cita) {
        CitaResponse response = new CitaResponse();
        response.setId(cita.getId());
        response.setMascotaId(cita.getMascota().getId());
        response.setNombreMascota(cita.getMascota().getNombre());
        response.setVeterinarioId(cita.getVeterinario().getId());
        response.setNombreVeterinario(cita.getVeterinario().getNombre());
        response.setFechaHora(cita.getFechaHora());
        response.setMotivo(cita.getMotivo());
        response.setEstado(cita.getEstado().name());
        return response;
    }
}