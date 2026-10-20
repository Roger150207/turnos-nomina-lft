package com.rogelio.turnosnominalft.modelo;

/**
 * Tipo de jornada (art. 60 LFT) y su limite diario ordinario (art. 61 LFT),
 * guardado en minutos.
 *
 * @author rogelio_sld
 */
public enum TipoJornada {
    DIURNA(480),
    NOCTURNA(420),
    MIXTA(450);

    private final int limiteDiarioMinutos;

    TipoJornada(int limiteDiarioMinutos) {
        this.limiteDiarioMinutos = limiteDiarioMinutos;
    }

    public int getLimiteDiarioMinutos() {
        return limiteDiarioMinutos;
    }
}
