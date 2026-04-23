package com.hospedagem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Casal: cama comum ou queen/king; berço opcional na reserva com taxa diária extra;
 * adicional de conforto conforme tipo de cama.
 */
@Entity
@Table(name = "quarto_duplo")
@DiscriminatorValue("DUPLO")
@PrimaryKeyJoinColumn(name = "quarto_id")
@Getter
@Setter
@NoArgsConstructor
public class QuartoDuplo extends Quarto {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cama_casal", nullable = false)
    private TipoCamaCasal tipoCamaCasal = TipoCamaCasal.COMUM;

    @Column(name = "adicional_conforto_comum", nullable = false)
    private double adicionalConfortoComum;

    @Column(name = "adicional_conforto_queen_king", nullable = false)
    private double adicionalConfortoQueenKing;

    @Column(name = "taxa_diaria_berco", nullable = false)
    private double taxaDiariaBerco;

    @Override
    public TipoQuarto getTipoQuarto() {
        return TipoQuarto.DUPLO;
    }

    @Override
    public boolean permiteBerco() {
        return true;
    }

    private double adicionalConfortoAtual() {
        return tipoCamaCasal == TipoCamaCasal.COMUM
                ? adicionalConfortoComum
                : adicionalConfortoQueenKing;
    }

    @Override
    public double calcularValorDiaria(int numeroHospedes, boolean solicitaBerco) {
        if (numeroHospedes < 1 || numeroHospedes > 2) {
            throw new IllegalArgumentException("Quarto duplo: entre 1 e 2 hóspedes.");
        }
        double diaria = getValorBase() + adicionalConfortoAtual();
        if (solicitaBerco) {
            diaria += taxaDiariaBerco;
        }
        return diaria;
    }

    @Override
    public int capacidadeMaximaHospedes() {
        return 2;
    }
}
