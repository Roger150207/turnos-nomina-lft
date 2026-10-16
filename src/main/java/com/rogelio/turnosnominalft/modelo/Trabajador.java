package com.rogelio.turnosnominalft.modelo;

import java.math.BigDecimal;
/**
 *
 * @author Saldaña Colorado Rogelio
 */
public class Trabajador {
    private String nombre;
    private BigDecimal salarioDiario;
    
    public Trabajador (String nombre,BigDecimal salarioDiario) {
        this.nombre = nombre;
        this.salarioDiario = salarioDiario;
    } 
    
    public String getNombre(){
        return nombre;
    }
    
    public BigDecimal getSalarioDiario(){
        return salarioDiario;
    }
}
