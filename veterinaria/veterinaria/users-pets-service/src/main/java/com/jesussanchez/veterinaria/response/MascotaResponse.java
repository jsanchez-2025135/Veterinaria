package com.jesussanchez.veterinaria.response;

import com.jesussanchez.veterinaria.entity.Especie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MascotaResponse {

    private Long id;
    private String nombre;
    private Especie especie;
    private String raza;
    private Integer edad;
    private Long clienteId;
    private String nombreCliente;
}