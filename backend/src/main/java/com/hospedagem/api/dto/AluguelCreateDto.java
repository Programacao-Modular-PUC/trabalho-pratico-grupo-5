package com.hospedagem.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AluguelCreateDto {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long quartoId;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    @Min(1)
    private int numeroHospedes;

    private boolean solicitaBerco;
}
