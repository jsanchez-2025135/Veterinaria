package com.jesussanchez.veterinaria.repository;

import com.jesussanchez.veterinaria.entity.ExpedienteClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpedienteRepository extends JpaRepository<ExpedienteClinico, Long> {
    List<ExpedienteClinico> finByCitaMascotaId(Long mascotaId);
}
