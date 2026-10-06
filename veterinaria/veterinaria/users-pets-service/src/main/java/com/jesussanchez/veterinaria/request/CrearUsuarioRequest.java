package com.jesussanchez.veterinaria.request;

import com.jesussanchez.veterinaria.entity.Rol;
import jakarta.validation.constraints.*;

public record CrearUsuarioRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 100) String nombre,
        @Size(max = 20) String telefono,
        @NotBlank(message = "El email es obligatorio") @Email(message = "Email inválido") @Size(max = 150) String email,
        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres") String password,
        @NotNull(message = "El rol es obligatorio") Rol rol
) {}