package com.jesussanchez.veterinaria.controller;

import com.jesussanchez.veterinaria.request.MascotaRequest;
import com.jesussanchez.veterinaria.response.MascotaResponse;
import com.jesussanchez.veterinaria.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }


    // CLIENTE: Ver sus propias mascotas
    @GetMapping("/mis-mascotas")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<MascotaResponse>> obtenerMisMascotas(Authentication auth) {
        return ResponseEntity.ok(mascotaService.obtenerPorCliente(auth.getName()));
    }

    // CLIENTE, ADMIN: Registrar mascota
    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<MascotaResponse> registrarMascota(
            @Valid @RequestBody MascotaRequest request,
            Authentication auth
    ) {
        boolean esAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mascotaService.registrarMascota(request, auth.getName(), esAdmin));
    }

    // VET, ADMIN: Consultar mascota por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('VET', 'ADMIN')")
    public ResponseEntity<MascotaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }


}