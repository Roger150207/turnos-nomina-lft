package com.rogelio.turnosnominalft.modelo;

import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author rogelio_sld
 */
public class SemanaTrabajada {
    private LocalDate fechaInicio;
    private List<DiaTrabajado> dias;

    public SemanaTrabajada(LocalDate fechaInicio, List<DiaTrabajado> dias) {
        // Lo pongo en el 1 de mayo por las reglas 
        if (fechaInicio.isBefore(LocalDate.of(2026, 5, 1))) {
            throw new IllegalArgumentException(" ERROR : La fecha es anterior a la reforma (1 mayo 2026) ");
        }

        if (dias.size() != 7) {
            throw new IllegalArgumentException(" ERROR : La semana debe tener 7 días ");
        }
        this.fechaInicio = fechaInicio;
        this.dias = dias;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public List<DiaTrabajado> getDias() {
        return dias;
    }
}