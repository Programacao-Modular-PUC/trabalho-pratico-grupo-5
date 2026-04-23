package com.hospedagem.api;

import com.hospedagem.api.dto.AluguelCreateDto;
import com.hospedagem.domain.Aluguel;
import com.hospedagem.service.AluguelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/alugueis")
@RequiredArgsConstructor
public class AluguelController {

    private final AluguelService aluguelService;

    @GetMapping
    public List<Aluguel> listar() {
        return aluguelService.listar();
    }

    @GetMapping("/{id}")
    public Aluguel obter(@PathVariable Long id) {
        return aluguelService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluguel criar(@RequestBody @Valid AluguelCreateDto dto) {
        return aluguelService.criar(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        aluguelService.remover(id);
    }
}
