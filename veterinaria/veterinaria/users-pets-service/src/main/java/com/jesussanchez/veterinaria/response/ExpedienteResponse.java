package com.jesussanchez.veterinaria.response;

import com.jesussanchez.veterinaria.entity.ExpedienteClinico;
import java.time.LocalDateTime;

public class ExpedienteResponse {
    private Long id;
    private Long citaId;
    private String diagnostico;
    private String tratamiento;
    private Double pesoKg;
    private LocalDateTime fechaRegistro;

    public static ExpedienteResponse fromEntity(ExpedienteClinico expediente) {
        ExpedienteResponse response = new ExpedienteResponse();
        response.setId(expediente.getId());
        response.setCitaId(expediente.getCita().getId());
        response.setDiagnostico(expediente.getDiagnostico());
        response.setTratamiento(expediente.getTratamiento());
        response.setPesoKg(expediente.getPesoKg());
        response.setFechaRegistro(expediente.getFechaRegistro());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}