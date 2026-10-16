/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rogelio.turnosnominalft.modelo;

import java.time.LocalTime;
import java.time.Duration;
/**
 *
 * @author rogelio_sld
 */
public class DiaTrabajado {
    private LocalTime entrada;
    private LocalTime salida;
    private int descansoMinutos;
    
    public DiaTrabajado (LocalTime entrada, LocalTime salida, int descansoMinutos){
        
        // Guardo los valores 
        this.entrada = entrada;
        this.salida = salida;
        this.descansoMinutos = descansoMinutos;
    }
        // Los paso a publicos 
        public LocalTime getEntrada(){
            return entrada;
        }
        
        public LocalTime getSalida(){
            return salida;
        }
        
        public int getDescansoMinutos(){
            return descansoMinutos;
        }
        
        // Calculo los minutos trabajados, ya sin el descanso
        public int minutosEfectivos(){
            long total = Duration.between(entrada, salida).toMinutes();

            if (salida.isBefore(entrada)) {
                total = total + 1440; 
            }
            return (int) total - descansoMinutos;
        }
        
}
