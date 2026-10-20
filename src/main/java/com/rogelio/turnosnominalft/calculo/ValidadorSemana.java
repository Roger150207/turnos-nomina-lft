package com.rogelio.turnosnominalft.calculo;

import com.rogelio.turnosnominalft.modelo.DiaTrabajado;
import com.rogelio.turnosnominalft.modelo.SemanaTrabajada;

/**
 * Revisa que los datos de la semana tengan sentido ANTES de calcular.
 * Si algo es imposible, lanza IllegalArgumentException con un mensaje claro.
 */
public class ValidadorSemana {

    public static void validar(SemanaTrabajada semana) {
        int diasTrabajados = 0;

        for (int i = 0; i < 7; i++) {
            DiaTrabajado dia = semana.getDias().get(i);
            if (dia == null) {
                continue; // dia sin trabajar
            }
            diasTrabajados++;

            if (dia.getDescansoMinutos() < 0) {
                throw new IllegalArgumentException(" ERROR : Dia " + (i + 1) + ": el descanso no puede ser negativo ");
            }
            if (dia.minutosEfectivos() <= 0) {
                throw new IllegalArgumentException(" ERROR : Dia " + (i + 1)
                        + ": no hay minutos trabajados (revisa entrada, salida y descanso) ");
            }
            if (dia.minutosDeReloj() > 1440) {
                throw new IllegalArgumentException(" ERROR : Dia " + (i + 1) + ": el turno no puede pasar de 24 horas ");
            }
        }

        if (diasTrabajados == 0) {
            throw new IllegalArgumentException(" ERROR : La semana no tiene ningun dia trabajado ");
        }
    }
}
