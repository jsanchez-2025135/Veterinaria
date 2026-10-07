package com.jesussanchez.veterinaria.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaRequest {
    @NotNull(message = "El id de la mascota es obligatorio")
    private Long mascotaId;

    @NotNull(message = "El id del veterinario es obligatorio")
    private Long veterinarioId;

    @NotNull(message = "La fecha y hora son obligatorias")
    @Future(message = "La cita debe ser programada para una fecha futura")
    private LocalDateTime fechaHora;

    private String motivo;

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}