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
 * Uma ou mais camas de solteiro; sem berço; diária = base + adicional por cama extra;
 * limite de hóspedes = número de camas.
 */
@Entity
@Table(name = "quarto_individual")
@DiscriminatorValue("INDIVIDUAL")
@PrimaryKeyJoinColumn(name = "quarto_id")
@Getter
@Setter
@NoArgsConstructor
public class QuartoIndividual extends Quarto {

    @Column(name = "numero_camas_solteiro", nullable = false)
    private int numeroCamasSolteiro = 1;

    /** Valor adicionado por cada cama além da primeira */
    @Column(name = "valor_adicional_por_cama", nullable = false)
    private double valorAdicionalPorCama;

    @Override
    public TipoQuarto getTipoQuarto() {
        return TipoQuarto.INDIVIDUAL;
    }

    @Override
    public double calcularValorDiaria(int numeroHospedes, boolean solicitaBerco) {
        if (solicitaBerco) {
            throw new IllegalArgumentException("Quarto individual não permite berço.");
        }
        if (numeroHospedes < 1 || numeroHospedes > numeroCamasSolteiro) {
            throw new IllegalArgumentException("Hóspedes devem estar entre 1 e " + numeroCamasSolteiro + ".");
        }
        if (numeroCamasSolteiro < 1) {
            throw new IllegalArgumentException("Deve existir pelo menos uma cama de solteiro.");
        }
        double diaria = getValorBase();
        if (numeroCamasSolteiro > 1) {
            diaria += (numeroCamasSolteiro - 1) * valorAdicionalPorCama;
        }
        return diaria;
    }

    @Override
    public int capacidadeMaximaHospedes() {
        return numeroCamasSolteiro;
    }
}
