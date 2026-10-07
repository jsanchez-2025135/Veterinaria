package com.jesussanchez.veterinaria.response;

import com.jesussanchez.veterinaria.entity.CitaMedica;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaResponse {
    private Long id;
    private Long mascotaId;
    private String nombreMascota;
    private Long veterinarioId;
    private String nombreVeterinario;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;

    public static CitaResponse fromEntity(CitaMedica cita) {
        CitaResponse response = new CitaResponse();
        response.setId(cita.getId());
        response.setMascotaId(cita.getMascota().getId());
        response.setNombreMascota(cita.getMascota().getNombre());
        response.setVeterinarioId(cita.getVeterinario().getId());
        response.setNombreVeterinario(cita.getVeterinario().getNombre());
        response.setFechaHora(cita.getFechaHora());
        response.setMotivo(cita.getMotivo());
        response.setEstado(cita.getEstado().name());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}