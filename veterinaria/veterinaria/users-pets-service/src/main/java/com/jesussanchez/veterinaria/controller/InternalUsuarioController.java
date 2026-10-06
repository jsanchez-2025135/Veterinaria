package com.jesussanchez.veterinaria.controller;

import com.jesussanchez.veterinaria.response.UsuarioResponse;
import com.jesussanchez.veterinaria.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internal/usuarios")
@RequiredArgsConstructor
public class InternalUsuarioController {

    private final UsuarioService usuarioService;

    public InternalUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public UsuarioResponse obtener(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }
}