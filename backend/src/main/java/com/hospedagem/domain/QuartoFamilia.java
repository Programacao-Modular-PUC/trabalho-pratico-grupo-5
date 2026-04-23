package com.hospedagem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Mistura de camas, capacidade derivada, ambientes distintos.
 * Diária: percentual sobre a base proporcional ao número de hóspedes;
 * desconto progressivo para grupos (vantagem vs vários individuais).
 */
@Entity
@Table(name = "quarto_familia")
@DiscriminatorValue("FAMILIA")
@PrimaryKeyJoinColumn(name = "quarto_id")
@Getter
@Setter
@NoArgsConstructor
public class QuartoFamilia extends Quarto {

    @Column(name = "camas_solteiro", nullable = false)
    private int camasSolteiro;

    @Column(name = "camas_casal_comuns", nullable = false)
    private int camasCasalComuns;

    @Column(name = "camas_queen_king", nullable = false)
    private int camasQueenKing;

    @Column(name = "ambientes_distintos", nullable = false)
    private int ambientesDistintos = 1;

    /**
     * Percentual extra sobre o valor base por hóspede acima do primeiro (ex.: 0.04 = 4% por pessoa extra).
     */
    @Column(name = "percentual_extra_por_hospede", nullable = false)
    private double percentualExtraPorHospede = 0.04;

    @Override
    public TipoQuarto getTipoQuarto() {
        return TipoQuarto.FAMILIA;
    }

    @Override
    public int capacidadeMaximaHospedes() {
        return camasSolteiro + 2 * camasCasalComuns + 2 * camasQueenKing;
    }

    /**
     * Pequeno acréscimo por ambiente além do primeiro (estudo, home office, etc.).
     */
    private double fatorAmbientes() {
        if (ambientesDistintos <= 1) {
            return 0;
        }
        return 0.02 * (ambientesDistintos - 1);
    }

    /**
     * Desconto progressivo para grupos: a partir de 4 pessoas, até 18% para tornar o quarto família
     * mais vantajoso que somar vários individuais.
     */
    private double descontoProgressivoGrupo(int numeroHospedes) {
        if (numeroHospedes < 4) {
            return 0;
        }
        return Math.min(0.18, 0.035 * (numeroHospedes - 3));
    }

    @Override
    public double calcularValorDiaria(int numeroHospedes, boolean solicitaBerco) {
        if (solicitaBerco) {
            throw new IllegalArgumentException("Solicitação de berço aplica-se ao quarto duplo neste modelo.");
        }
        int cap = capacidadeMaximaHospedes();
        if (cap < 1) {
            throw new IllegalArgumentException("Configure ao menos uma cama no quarto família.");
        }
        if (numeroHospedes < 1 || numeroHospedes > cap) {
            throw new IllegalArgumentException("Número de hóspedes deve estar entre 1 e " + cap + ".");
        }
        double multiplicadorHospedes = 1 + percentualExtraPorHospede * (numeroHospedes - 1);
        double valor = getValorBase() * multiplicadorHospedes;
        valor *= (1 + fatorAmbientes());
        double desconto = descontoProgressivoGrupo(numeroHospedes);
        return valor * (1 - desconto);
    }
}
