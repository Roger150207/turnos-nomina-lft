package com.rogelio.turnosnominalft.modelo;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Un dia de trabajo: hora de entrada, hora de salida y minutos de descanso.
 *
 * @author rogelio_sld
 */
public class DiaTrabajado {
    private LocalTime entrada;
    private LocalTime salida;
    private int descansoMinutos;

    public DiaTrabajado(LocalTime entrada, LocalTime salida, int descansoMinutos) {
        // Guardo los valores
        this.entrada = entrada;
        this.salida = salida;
        this.descansoMinutos = descansoMinutos;
    }

    public LocalTime getEntrada() {
        return entrada;
    }

    public LocalTime getSalida() {
        return salida;
    }

    public int getDescansoMinutos() {
        return descansoMinutos;
    }

    // Minutos que pasaron en el reloj entre entrada y salida (sin quitar descanso)
    public int minutosDeReloj() {
        long total = Duration.between(entrada, salida).toMinutes();

        // Si la salida es antes que la entrada, el turno cruzo la medianoche
        if (salida.isBefore(entrada)) {
            total = total + 1440; // le sumo un dia completo en minutos
        }
        return (int) total;
    }

    // Minutos realmente trabajados: reloj menos descanso
    public int minutosEfectivos() {
        return minutosDeReloj() - descansoMinutos;
    }
}
