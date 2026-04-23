package com.hospedagem.service;

import com.hospedagem.api.dto.AluguelCreateDto;
import com.hospedagem.domain.Aluguel;
import com.hospedagem.domain.Cliente;
import com.hospedagem.domain.Quarto;
import com.hospedagem.repository.AluguelRepository;
import com.hospedagem.repository.ClienteRepository;
import com.hospedagem.repository.QuartoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final ClienteRepository clienteRepository;
    private final QuartoRepository quartoRepository;

    public List<Aluguel> listar() {
        return aluguelRepository.findAll();
    }

    public Aluguel buscar(Long id) {
        return aluguelRepository.findDetalhe(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluguel não encontrado: " + id));
    }

    @Transactional
    public Aluguel criar(AluguelCreateDto dto) {
        if (!dto.getDataFim().isAfter(dto.getDataInicio())) {
            throw new IllegalArgumentException("Data fim deve ser posterior à data início.");
        }
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        Quarto quarto = quartoRepository.findById(dto.getQuartoId())
                .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado."));

        if (dto.getNumeroHospedes() < 1) {
            throw new IllegalArgumentException("Número de hóspedes deve ser >= 1.");
        }
        if (dto.getNumeroHospedes() > quarto.capacidadeMaximaHospedes()) {
            throw new IllegalArgumentException("Número de hóspedes excede a capacidade do quarto (" +
                    quarto.capacidadeMaximaHospedes() + ").");
        }
        if (dto.isSolicitaBerco() && !quarto.permiteBerco()) {
            throw new IllegalArgumentException("Este tipo de quarto não permite berço.");
        }

        long noites = ChronoUnit.DAYS.between(dto.getDataInicio(), dto.getDataFim());
        if (noites < 1) {
            throw new IllegalArgumentException("Período deve ter pelo menos uma diária (uma noite).");
        }

        double diaria = quarto.calcularValorDiaria(dto.getNumeroHospedes(), dto.isSolicitaBerco());
        Aluguel aluguel = new Aluguel();
        aluguel.setValorTotal(diaria * noites);
        aluguel.setCliente(cliente);
        aluguel.setQuarto(quarto);
        aluguel.setDataInicio(dto.getDataInicio());
        aluguel.setDataFim(dto.getDataFim());
        aluguel.setNumeroHospedes(dto.getNumeroHospedes());
        aluguel.setSolicitaBerco(dto.isSolicitaBerco());
        return aluguelRepository.save(aluguel);
    }

    @Transactional
    public void remover(Long id) {
        aluguelRepository.deleteById(id);
    }
}
