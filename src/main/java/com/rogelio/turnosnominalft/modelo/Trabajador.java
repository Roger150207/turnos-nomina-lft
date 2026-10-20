package com.rogelio.turnosnominalft.modelo;

import java.math.BigDecimal;

/**
 * Datos del trabajador: nombre y salario diario.
 *
 * @author Saldaña Colorado Rogelio
 */
public class Trabajador {
    private String nombre;
    private BigDecimal salarioDiario;

    public Trabajador(String nombre, BigDecimal salarioDiario) {
        if (salarioDiario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(" ERROR : No puedes ganar 0 pesos o menos ");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(" ERROR : Deberas registrar un nombre por lo menos ");
        }
        this.nombre = nombre;
        this.salarioDiario = salarioDiario;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getSalarioDiario() {
        return salarioDiario;
    }
}
