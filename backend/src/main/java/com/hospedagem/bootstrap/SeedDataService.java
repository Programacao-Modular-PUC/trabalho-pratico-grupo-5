package com.hospedagem.bootstrap;

import com.hospedagem.api.dto.AluguelCreateDto;
import com.hospedagem.domain.Cliente;
import com.hospedagem.domain.QuartoDuplo;
import com.hospedagem.domain.QuartoFamilia;
import com.hospedagem.domain.QuartoIndividual;
import com.hospedagem.domain.Residencia;
import com.hospedagem.domain.TipoCamaCasal;
import com.hospedagem.repository.ClienteRepository;
import com.hospedagem.repository.ResidenciaRepository;
import com.hospedagem.repository.QuartoRepository;
import com.hospedagem.service.AluguelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SeedDataService {

    private final ResidenciaRepository residenciaRepository;
    private final ClienteRepository clienteRepository;
    private final QuartoRepository quartoRepository;
    private final AluguelService aluguelService;

    @Transactional
    public boolean seedIfEmpty() {
        if (residenciaRepository.count() > 0) {
            return false;
        }

        Cliente c1 = new Cliente();
        c1.setNome("Maria Silva");
        c1.setEmail("maria.silva@example.com");
        c1.setDocumento("11122233344");
        c1 = clienteRepository.save(c1);

        Cliente c2 = new Cliente();
        c2.setNome("João Santos");
        c2.setEmail("joao.santos@example.com");
        c2.setDocumento("55566677788");
        c2 = clienteRepository.save(c2);

        Residencia res = new Residencia();
        res.setNome("Residência Vista Mar");
        res.setEndereco("Rua das Palmeiras, 100 — Florianópolis/SC");
        res = residenciaRepository.save(res);

        QuartoIndividual ind = new QuartoIndividual();
        ind.setResidencia(res);
        ind.setValorBase(120);
        ind.setPossuiAR(true);
        ind.setPossuiHidro(false);
        ind.setNumeroCamasSolteiro(2);
        ind.setValorAdicionalPorCama(35);
        ind = quartoRepository.save(ind);

        QuartoDuplo dup = new QuartoDuplo();
        dup.setResidencia(res);
        dup.setValorBase(220);
        dup.setPossuiAR(true);
        dup.setPossuiHidro(true);
        dup.setTipoCamaCasal(TipoCamaCasal.QUEEN_KING);
        dup.setAdicionalConfortoComum(25);
        dup.setAdicionalConfortoQueenKing(45);
        dup.setTaxaDiariaBerco(40);
        dup = quartoRepository.save(dup);

        QuartoFamilia fam = new QuartoFamilia();
        fam.setResidencia(res);
        fam.setValorBase(380);
        fam.setPossuiAR(true);
        fam.setPossuiHidro(false);
        fam.setCamasSolteiro(2);
        fam.setCamasCasalComuns(1);
        fam.setCamasQueenKing(0);
        fam.setAmbientesDistintos(2);
        fam.setPercentualExtraPorHospede(0.04);
        fam = quartoRepository.save(fam);

        LocalDate today = LocalDate.now();

        AluguelCreateDto aluguel1 = new AluguelCreateDto();
        aluguel1.setClienteId(c1.getId());
        aluguel1.setQuartoId(ind.getId());
        aluguel1.setDataInicio(today.minusDays(25));
        aluguel1.setDataFim(today.minusDays(22));
        aluguel1.setNumeroHospedes(1);
        aluguel1.setSolicitaBerco(false);
        aluguelService.criar(aluguel1);

        AluguelCreateDto aluguel2 = new AluguelCreateDto();
        aluguel2.setClienteId(c2.getId());
        aluguel2.setQuartoId(dup.getId());
        aluguel2.setDataInicio(today.plusDays(5));
        aluguel2.setDataFim(today.plusDays(8));
        aluguel2.setNumeroHospedes(2);
        aluguel2.setSolicitaBerco(true);
        aluguelService.criar(aluguel2);

        AluguelCreateDto aluguel3 = new AluguelCreateDto();
        aluguel3.setClienteId(c1.getId());
        aluguel3.setQuartoId(fam.getId());
        aluguel3.setDataInicio(today.plusDays(14));
        aluguel3.setDataFim(today.plusDays(18));
        aluguel3.setNumeroHospedes(4);
        aluguel3.setSolicitaBerco(false);
        aluguelService.criar(aluguel3);
        return true;
    }
}
