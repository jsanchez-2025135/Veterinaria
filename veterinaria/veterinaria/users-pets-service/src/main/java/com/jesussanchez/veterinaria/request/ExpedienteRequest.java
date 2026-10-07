package com.jesussanchez.veterinaria.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpedienteRequest {
    @NotNull(message = "El id de la cita es obligatorio")
    private Long citaId;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    @NotBlank(message = "El tratamiento es obligatorio")
    private String tratamiento;

    private Double pesoKg;
}