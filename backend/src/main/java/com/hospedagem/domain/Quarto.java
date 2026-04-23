package com.hospedagem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Atributos comuns: id, valorBase, possuiAR, possuiHidro.
 * Subtipos implementam regras de diária e capacidade.
 */
@Entity
@Table(name = "quarto")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_quarto", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "tipoQuarto", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = QuartoIndividual.class, name = "INDIVIDUAL"),
        @JsonSubTypes.Type(value = QuartoDuplo.class, name = "DUPLO"),
        @JsonSubTypes.Type(value = QuartoFamilia.class, name = "FAMILIA")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public abstract class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_base", nullable = false)
    private double valorBase;

    @Column(name = "possui_ar", nullable = false)
    private boolean possuiAR;

    @Column(name = "possui_hidro", nullable = false)
    private boolean possuiHidro;

    @ManyToOne(optional = false)
    @JoinColumn(name = "residencia_id")
    @JsonIgnore
    private Residencia residencia;

    public abstract TipoQuarto getTipoQuarto();

    /**
     * Valor de uma diária conforme regras do tipo de quarto.
     *
     * @param numeroHospedes   hóspedes no período (relevante para família e limites)
     * @param solicitaBerco    apenas quarto duplo: taxa extra se true
     */
    public abstract double calcularValorDiaria(int numeroHospedes, boolean solicitaBerco);

    /** Capacidade máxima de hóspedes permitida neste quarto. */
    public abstract int capacidadeMaximaHospedes();

    public boolean permiteBerco() {
        return false;
    }
}
