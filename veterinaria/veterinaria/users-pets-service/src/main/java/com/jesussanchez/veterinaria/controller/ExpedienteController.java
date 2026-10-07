package com.jesussanchez.veterinaria.controller;

import com.jesussanchez.veterinaria.entity.ExpedienteClinico;
import com.jesussanchez.veterinaria.request.ExpedienteRequest;
import com.jesussanchez.veterinaria.response.ExpedienteResponse;
import com.jesussanchez.veterinaria.service.ExpedienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/expedientes")
public class ExpedienteController {

    private final ExpedienteService expedienteService;

    public ExpedienteController(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VET', 'ADMIN')")
    public ResponseEntity<ExpedienteResponse> registrarExpediente(@Valid @RequestBody ExpedienteRequest request) {
        ExpedienteClinico expediente = expedienteService.registrarExpediente(
                request.getCitaId(),
                request.getDiagnostico(),
                request.getTratamiento(),
                request.getPesoKg()
        );
        return ResponseEntity.ok(ExpedienteResponse.fromEntity(expediente));
    }

    @GetMapping("/mascota/{mascotaId}")
    @PreAuthorize("hasAnyRole('VET', 'CLIENTE', 'ADMIN')")
    public ResponseEntity<List<ExpedienteResponse>> obtenerHistorialPorMascota(@PathVariable Long mascotaId) {
        List<ExpedienteResponse> expedientes = expedienteService.obtenerHistorialPorMascota(mascotaId).stream()
                .map(ExpedienteResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(expedientes);
    }
}