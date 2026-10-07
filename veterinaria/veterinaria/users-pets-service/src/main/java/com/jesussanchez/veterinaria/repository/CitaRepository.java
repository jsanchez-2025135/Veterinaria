package com.jesussanchez.veterinaria.repository;

import com.jesussanchez.veterinaria.entity.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<CitaMedica, Long> {
    List<CitaMedica> findByVeterinarioIdAndEstadoNot(Long veterinarioId, CitaMedica.EstadoCita estado);

    List<CitaMedica> findByMascotaClienteIdAndEstadoAndFechaHoraBetween(
            Long clienteId, CitaMedica.EstadoCita estado, LocalDateTime inicio, LocalDateTime fin);
}