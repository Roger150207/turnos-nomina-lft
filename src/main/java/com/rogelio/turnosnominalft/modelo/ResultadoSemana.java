package com.rogelio.turnosnominalft.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Resultado de toda la semana: los dias calculados y los totales.
 * Los totales se suman una sola vez, aqui en el constructor.
 */
public class ResultadoSemana {
    private final Trabajador trabajador;
    private final LocalDate fechaInicio;
    private final int anioReglas; // anio de la tabla de reglas que se uso
    private final List<ResultadoDia> dias;
    private final List<String> advertencias;

    private int minutosDobles = 0;
    private int minutosTriples = 0;
    private int minutosFueraDeTope = 0;
    private BigDecimal pagoDobles = BigDecimal.ZERO;
    private BigDecimal pagoTriples = BigDecimal.ZERO;
    private BigDecimal bono = BigDecimal.ZERO;

    public ResultadoSemana(Trabajador trabajador, LocalDate fechaInicio, int anioReglas,
                           List<ResultadoDia> dias, List<String> advertencias) {
        this.trabajador = trabajador;
        this.fechaInicio = fechaInicio;
        this.anioReglas = anioReglas;
        this.dias = dias;
        this.advertencias = advertencias;

        // Sumo los totales de la semana
        for (ResultadoDia d : dias) {
            minutosDobles += d.getMinutosDobles();
            minutosTriples += d.getMinutosTriples();
            minutosFueraDeTope += d.getMinutosFueraDeTope();
            pagoDobles = pagoDobles.add(d.getPagoDobles());
            pagoTriples = pagoTriples.add(d.getPagoTriples());
            bono = bono.add(d.getBono());
        }
    }

    // Lo que se paga ademas del salario normal: extras + bono
    public BigDecimal getTotal() {
        return pagoDobles.add(pagoTriples).add(bono);
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public int getAnioReglas() {
        return anioReglas;
    }

    public List<ResultadoDia> getDias() {
        return dias;
    }

    public List<String> getAdvertencias() {
        return advertencias;
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
