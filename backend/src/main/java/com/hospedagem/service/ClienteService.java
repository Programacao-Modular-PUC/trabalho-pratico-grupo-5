package com.hospedagem.service;

import com.hospedagem.domain.Cliente;
import com.hospedagem.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscar(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
    }

    @Transactional
    public Cliente criar(Cliente c) {
        c.setId(null);
        return clienteRepository.save(c);
    }

    @Transactional
    public Cliente atualizar(Long id, Cliente dados) {
        Cliente existente = buscar(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setDocumento(dados.getDocumento());
        return clienteRepository.save(existente);
    }

    @Transactional
    public void remover(Long id) {
        clienteRepository.deleteById(id);
    }
}
