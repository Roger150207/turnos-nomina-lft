package com.rogelio.turnosnominalft;

import com.rogelio.turnosnominalft.modelo.Trabajador;
import java.math.BigDecimal;
import com.rogelio.turnosnominalft.modelo.TipoJornada;
import com.rogelio.turnosnominalft.modelo.DiaTrabajado;
import java.time.LocalTime;

/**
 *
 * @author rogelio_sld
 */
public class TurnosNominaLft {

    public static void main(String[] args) {
        // Creo un nuevo trabajador y lo imprimo 
        Trabajador trabajador = new Trabajador ("Rogelio Saldaña", new BigDecimal ("600.99"));
        System.out.println(" NOMBRE : " + trabajador.getNombre() + " SALARIO X DIA : " + trabajador.getSalarioDiario() );
        
        //  Imprimo los limites de minutos segun el turno 
        System.out.println(" MINUTOS DIURNA : " + TipoJornada.DIURNA.getLimiteDiarioMinutos());
        System.out.println(" MINUTOS NOCTURNO : " + TipoJornada.NOCTURNO.getLimiteDiarioMinutos());
        System.out.println(" MINUTOS MIXTO : " + TipoJornada.MIXTO.getLimiteDiarioMinutos());
        
        // Creo un horario de prueba 
        DiaTrabajado diaTrabajado = new DiaTrabajado(LocalTime.of(9, 0), LocalTime.of(17, 0), 30);        
        // Imprimo la prueba de horas 
        System.out.println(" ENTRADA " + diaTrabajado.getEntrada());
        System.out.println(" SALIDA " + diaTrabajado.getSalida());
        System.out.println(" DESCANSO  " + diaTrabajado.getDescansoMinutos() + " minutos ");
    
        // Imprimimos los minutos efectivos 
        System.out.println(" MINUTOS EFECTIVOS : " + diaTrabajado.minutosEfectivos());
        
        // Pruebo un turno nocturno que cruza la medianoche
        DiaTrabajado nocturno = new DiaTrabajado(LocalTime.of(22, 0), LocalTime.of(6, 0), 30);
        System.out.println(" MINUTOS EFECTIVOS NOCTURNO : " + nocturno.minutosEfectivos());
    }
}
