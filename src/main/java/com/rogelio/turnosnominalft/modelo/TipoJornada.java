/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.rogelio.turnosnominalft.modelo;

/**
 *
 * @author rogelio_sld
 */
public enum TipoJornada {
    DIURNA(480),
    NOCTURNO(420),
    MIXTO(450);
    
    private final int limiteDiarioMinutos;
    
    TipoJornada (int limiteDiarioMinutos){
        this.limiteDiarioMinutos = limiteDiarioMinutos;
    }
    
    public int getLimiteDiarioMinutos(){
        return limiteDiarioMinutos;
    }
}
