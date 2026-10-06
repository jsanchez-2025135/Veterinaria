package com.jesussanchez.veterinaria.response;

import com.jesussanchez.veterinaria.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String telefono;
    private String email;
    private Rol rol;
}