package com.jesussanchez.veterinaria.controller;

import com.jesussanchez.veterinaria.entity.CitaMedica;
import com.jesussanchez.veterinaria.request.CitaRequest;
import com.jesussanchez.veterinaria.response.CitaResponse;
import com.jesussanchez.veterinaria.service.CitaService;
import com.jesussanchez.veterinaria.repository.CitaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    private final CitaService citaService;
    private final CitaRepository citaRepository;

    public CitaController(CitaService citaService, CitaRepository citaRepository) {
        this.citaService = citaService;
        this.citaRepository = citaRepository;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<CitaResponse> agendarCita(@Valid @RequestBody CitaRequest request, Authentication authentication) {
        String emailUsuario = authentication.getName();
        CitaMedica cita = citaService.agendarCita(
                request.getMascotaId(),
                request.getVeterinarioId(),
                request.getFechaHora(),
                request.getMotivo(),
                emailUsuario
        );
        return ResponseEntity.ok(CitaResponse.fromEntity(cita));
    }

    @GetMapping("/agenda")
    @PreAuthorize("hasAnyRole('VET', 'ADMIN')")
    public ResponseEntity<List<CitaResponse>> listarAgenda() {
        List<CitaResponse> citas = citaRepository.findAll().stream()
                .map(CitaResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(citas);
    }

    @PatchMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<Void> cancelarCita(@PathVariable Long id, Authentication authentication) {
        String emailUsuario = authentication.getName();
        citaService.cancelarCita(id, emailUsuario);
        return ResponseEntity.noContent().build();
    }
}