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
}