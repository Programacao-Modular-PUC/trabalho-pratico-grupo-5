package com.hospedagem.repository;

import com.hospedagem.domain.Residencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResidenciaRepository extends JpaRepository<Residencia, Long> {

    @Query("SELECT DISTINCT r FROM Residencia r LEFT JOIN FETCH r.quartos WHERE r.id = :id")
    Optional<Residencia> findDetalhe(@Param("id") Long id);
}
