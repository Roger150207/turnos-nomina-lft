package com.rogelio.turnosnominalft.calculo;

import com.rogelio.turnosnominalft.modelo.DiaTrabajado;
import com.rogelio.turnosnominalft.modelo.TipoJornada;
import java.time.LocalTime;

/**
 * Decide si un dia es diurna, nocturna o mixta (art. 60 LFT):
 * diurna 06:00-20:00, nocturna 20:00-06:00,
 * mixta si el periodo nocturno es menor a 3.5 h; si no, nocturna.
 */
public class ClasificadorJornada {

    private static final LocalTime INICIO_NOCHE = LocalTime.of(20, 0);
    private static final LocalTime FIN_NOCHE = LocalTime.of(6, 0);
    private static final int LIMITE_MIXTA_MIN = 210; // 3 h 30 min

    public static TipoJornada clasificar(DiaTrabajado dia) {
        int minutosDeNoche = 0;

        // Reviso minuto por minuto del reloj si cae dentro de la noche
        for (int i = 0; i < dia.minutosDeReloj(); i++) {
            LocalTime hora = dia.getEntrada().plusMinutes(i);
            boolean esNoche = !hora.isBefore(INICIO_NOCHE) || hora.isBefore(FIN_NOCHE);
            if (esNoche) {
                minutosDeNoche++;
            }
        }

        if (minutosDeNoche == 0) {
            return TipoJornada.DIURNA;
        }
        if (minutosDeNoche < LIMITE_MIXTA_MIN) {
            return TipoJornada.MIXTA;
        }
        return TipoJornada.NOCTURNA;
    }
}
