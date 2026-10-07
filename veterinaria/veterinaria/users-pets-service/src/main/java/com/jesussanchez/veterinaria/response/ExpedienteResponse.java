package com.jesussanchez.veterinaria.response;

import com.jesussanchez.veterinaria.entity.ExpedienteClinico;
import lombok.Data;
import java.time.LocalDateTime;

@Data
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
}