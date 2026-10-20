package com.rogelio.turnosnominalft.reglas;

/**
 * Topes de la ley para UN año (todo en minutos).
 */
public class TablaReglas {
    private final int anio;
    private final int maxSemanalOrdinarioMin;
    private final int maxDoblesMin;        
    private final int maxTriplesMin;          
    private final int maxExtraDiaMin;         
    private final int maxTotalDiaMin;         
    private final int maxDiasConExtra;      

    public TablaReglas(int anio, int maxSemanalOrdinarioMin, int maxDoblesMin, int maxTriplesMin,
                       int maxExtraDiaMin, int maxTotalDiaMin, int maxDiasConExtra) {
        this.anio = anio;
        this.maxSemanalOrdinarioMin = maxSemanalOrdinarioMin;
        this.maxDoblesMin = maxDoblesMin;
        this.maxTriplesMin = maxTriplesMin;
        this.maxExtraDiaMin = maxExtraDiaMin;
        this.maxTotalDiaMin = maxTotalDiaMin;
        this.maxDiasConExtra = maxDiasConExtra;
    }

    public int getAnio() {
        return anio;
    }

    public int getMaxSemanalOrdinarioMin() {
        return maxSemanalOrdinarioMin;
    }

    public int getMaxDoblesMin() {
        return maxDoblesMin;
    }

    public int getMaxTriplesMin() {
        return maxTriplesMin;
    }

    public int getMaxExtraDiaMin() {
        return maxExtraDiaMin;
    }

    public int getMaxTotalDiaMin() {
        return maxTotalDiaMin;
    }

    public int getMaxDiasConExtra() {
        return maxDiasConExtra;
    }
}
