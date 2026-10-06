package com.jesussanchez.veterinaria.request;

import com.jesussanchez.veterinaria.entity.Especie;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MascotaRequest {

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    private String nombre;

    @NotNull(message = "La especie es obligatoria")
    private Especie especie;

    private String raza;

    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edad;

    private Long clienteId; // Opcional: solo si un ADMIN registra la mascota para otro cliente
}