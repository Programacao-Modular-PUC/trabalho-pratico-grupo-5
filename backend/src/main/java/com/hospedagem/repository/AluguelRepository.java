package com.hospedagem.repository;

import com.hospedagem.domain.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    @Query("SELECT a FROM Aluguel a JOIN FETCH a.cliente JOIN FETCH a.quarto WHERE a.id = :id")
    Optional<Aluguel> findDetalhe(@Param("id") Long id);
}
