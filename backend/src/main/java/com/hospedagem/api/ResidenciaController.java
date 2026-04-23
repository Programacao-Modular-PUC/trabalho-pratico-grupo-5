package com.hospedagem.api;

import com.hospedagem.domain.Residencia;
import com.hospedagem.service.ResidenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/residencias")
@RequiredArgsConstructor
public class ResidenciaController {

    private final ResidenciaService residenciaService;

    @GetMapping
    public List<Residencia> listar() {
        return residenciaService.listar();
    }

    @GetMapping("/{id}")
    public Residencia obter(@PathVariable Long id) {
        return residenciaService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Residencia criar(@RequestBody @Valid Residencia body) {
        return residenciaService.criar(body);
    }

    @PutMapping("/{id}")
    public Residencia atualizar(@PathVariable Long id, @RequestBody @Valid Residencia body) {
        return residenciaService.atualizar(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        residenciaService.remover(id);
    }
}
