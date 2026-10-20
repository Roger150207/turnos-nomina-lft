package com.rogelio.turnosnominalft.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Una semana de 7 dias a partir de una fecha de inicio.
 * Un dia que no se trabajo va como null en la lista.
 *
 * @author rogelio_sld
 */
public class SemanaTrabajada {
    private LocalDate fechaInicio;
    private List<DiaTrabajado> dias;

    public SemanaTrabajada(LocalDate fechaInicio, List<DiaTrabajado> dias) {
        // Las reglas nuevas de la LFT empiezan el 1 de mayo de 2026
        if (fechaInicio.isBefore(LocalDate.of(2026, 5, 1))) {
            throw new IllegalArgumentException(" ERROR : La fecha es anterior a la reforma (1 mayo 2026) ");
        }
        // Una semana siempre trae 7 lugares (un dia sin trabajar va como null)
        if (dias.size() != 7) {
            throw new IllegalArgumentException(" ERROR : La semana debe tener 7 dias ");
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
