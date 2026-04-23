package com.hospedagem.api;

import com.hospedagem.domain.Quarto;
import com.hospedagem.domain.QuartoDuplo;
import com.hospedagem.domain.QuartoFamilia;
import com.hospedagem.domain.QuartoIndividual;
import com.hospedagem.service.QuartoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quartos")
@RequiredArgsConstructor
public class QuartoController {

    private final QuartoService quartoService;

    @GetMapping
    public List<Quarto> listar(@RequestParam(required = false) Long residenciaId) {
        if (residenciaId != null) {
            return quartoService.listarPorResidencia(residenciaId);
        }
        return quartoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Quarto obter(@PathVariable Long id) {
        return quartoService.buscar(id);
    }

    /**
     * Corpo JSON deve incluir {@code "tipoQuarto":"INDIVIDUAL"|"DUPLO"|"FAMILIA"} e os campos do subtipo.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quarto criar(@RequestParam Long residenciaId, @RequestBody Quarto payload) {
        payload.setId(null);
        payload.setResidencia(null);
        switch (payload.getTipoQuarto()) {
            case INDIVIDUAL:
                return quartoService.criarIndividual(residenciaId, (QuartoIndividual) payload);
            case DUPLO:
                return quartoService.criarDuplo(residenciaId, (QuartoDuplo) payload);
            case FAMILIA:
                return quartoService.criarFamilia(residenciaId, (QuartoFamilia) payload);
            default:
                throw new IllegalArgumentException("Tipo de quarto inválido: " + payload.getTipoQuarto());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        quartoService.remover(id);
    }
}
