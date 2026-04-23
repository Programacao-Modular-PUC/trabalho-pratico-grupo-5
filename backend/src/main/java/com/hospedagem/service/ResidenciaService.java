package com.hospedagem.service;

import com.hospedagem.domain.Residencia;
import com.hospedagem.repository.ResidenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidenciaService {

    private final ResidenciaRepository residenciaRepository;

    public List<Residencia> listar() {
        return residenciaRepository.findAll();
    }

    public Residencia buscar(Long id) {
        return residenciaRepository.findDetalhe(id)
                .orElseThrow(() -> new IllegalArgumentException("Residência não encontrada: " + id));
    }

    @Transactional
    public Residencia criar(Residencia r) {
        r.setId(null);
        return residenciaRepository.save(r);
    }

    @Transactional
    public Residencia atualizar(Long id, Residencia dados) {
        Residencia existente = buscar(id);
        existente.setNome(dados.getNome());
        existente.setEndereco(dados.getEndereco());
        return residenciaRepository.save(existente);
    }

    @Transactional
    public void remover(Long id) {
        residenciaRepository.deleteById(id);
    }
}
