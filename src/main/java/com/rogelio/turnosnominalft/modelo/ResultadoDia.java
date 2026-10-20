package com.rogelio.turnosnominalft.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Lo que salio de calcular UN dia: como se repartieron sus minutos y cuanto se paga.
 * Es solo una "comanda" con resultados (no calcula nada por si sola).
 */
public class ResultadoDia {
    private final LocalDate fecha;
    private final TipoJornada tipo;
    private final int minutosEfectivos;
    private final int minutosOrdinarios;
    private final int minutosDobles;
    private final int minutosTriples;
    private final int minutosFueraDeTope;
    private final BigDecimal valorHora;
    private final BigDecimal pagoDobles;
    private final BigDecimal pagoTriples;
    private final BigDecimal bono;

    public ResultadoDia(LocalDate fecha, TipoJornada tipo, int minutosEfectivos, int minutosOrdinarios,
                        int minutosDobles, int minutosTriples, int minutosFueraDeTope,
                        BigDecimal valorHora, BigDecimal pagoDobles, BigDecimal pagoTriples, BigDecimal bono) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.minutosEfectivos = minutosEfectivos;
        this.minutosOrdinarios = minutosOrdinarios;
        this.minutosDobles = minutosDobles;
        this.minutosTriples = minutosTriples;
        this.minutosFueraDeTope = minutosFueraDeTope;
        this.valorHora = valorHora;
        this.pagoDobles = pagoDobles;
        this.pagoTriples = pagoTriples;
        this.bono = bono;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public TipoJornada getTipo() {
        return tipo;
    }

    public int getMinutosEfectivos() {
        return minutosEfectivos;
    }

    public int getMinutosOrdinarios() {
        return minutosOrdinarios;
    }

    public int getMinutosDobles() {
        return minutosDobles;
    }

    public int getMinutosTriples() {
        return minutosTriples;
    }

    public int getMinutosFueraDeTope() {
        return minutosFueraDeTope;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public BigDecimal getPagoDobles() {
        return pagoDobles;
    }

    public BigDecimal getPagoTriples() {
        return pagoTriples;
    }

    public BigDecimal getBono() {
        return bono;
    }
}
