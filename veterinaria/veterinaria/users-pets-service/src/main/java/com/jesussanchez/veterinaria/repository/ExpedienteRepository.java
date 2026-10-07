package com.jesussanchez.veterinaria.repository;

import com.jesussanchez.veterinaria.entity.ExpedienteClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpedienteRepository extends JpaRepository<ExpedienteClinico, Long> {

    // Usamos @Query para asegurar que encuentre exactamente este nombre de método
    @Query("SELECT e FROM ExpedienteClinico e WHERE e.cita.mascota.id = :mascotaId")
    List<ExpedienteClinico> findByCitaMascotaId(@Param("mascotaId") Long mascotaId);
}