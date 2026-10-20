package com.rogelio.turnosnominalft.reglas;

import com.rogelio.turnosnominalft.modelo.TipoJornada;
import java.math.BigDecimal;

/**
 * Bono fijo por turno trabajado.opcional
 */
public class ConfigBono {

    public static BigDecimal bonoPara(TipoJornada tipo) {
        return switch (tipo) {
            case DIURNA -> BigDecimal.ZERO;
            case MIXTA -> new BigDecimal("75");
            case NOCTURNA -> new BigDecimal("150");
        };
    }
}
