package com.hospedagem.service;

import com.hospedagem.domain.Quarto;
import com.hospedagem.domain.QuartoDuplo;
import com.hospedagem.domain.QuartoFamilia;
import com.hospedagem.domain.QuartoIndividual;
import com.hospedagem.domain.Residencia;
import com.hospedagem.repository.QuartoRepository;
import com.hospedagem.repository.ResidenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuartoService {

    private final QuartoRepository quartoRepository;
    private final ResidenciaRepository residenciaRepository;

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public List<Quarto> listarPorResidencia(Long residenciaId) {
        return quartoRepository.findByResidenciaId(residenciaId);
    }

    public Quarto buscar(Long id) {
        return quartoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado: " + id));
    }

    private Residencia residenciaObrigatoria(Long residenciaId) {
        return residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new IllegalArgumentException("Residência não encontrada: " + residenciaId));
    }

    @Transactional
    public QuartoIndividual criarIndividual(Long residenciaId, QuartoIndividual q) {
        q.setId(null);
        q.setResidencia(residenciaObrigatoria(residenciaId));
        if (q.getNumeroCamasSolteiro() < 1) {
            throw new IllegalArgumentException("Número de camas de solteiro deve ser >= 1.");
        }
        return quartoRepository.save(q);
    }

    @Transactional
    public QuartoDuplo criarDuplo(Long residenciaId, QuartoDuplo q) {
        q.setId(null);
        q.setResidencia(residenciaObrigatoria(residenciaId));
        return quartoRepository.save(q);
    }

    @Transactional
    public QuartoFamilia criarFamilia(Long residenciaId, QuartoFamilia q) {
        q.setId(null);
        q.setResidencia(residenciaObrigatoria(residenciaId));
        if (q.capacidadeMaximaHospedes() < 1) {
            throw new IllegalArgumentException("Configure camas para capacidade mínima de 1 hóspede.");
        }
        return quartoRepository.save(q);
    }

    @Transactional
    public void remover(Long id) {
        quartoRepository.deleteById(id);
    }
}
