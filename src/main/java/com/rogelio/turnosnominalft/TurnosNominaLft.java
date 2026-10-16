package com.rogelio.turnosnominalft;

import com.rogelio.turnosnominalft.modelo.Trabajador;
import java.math.BigDecimal;
import com.rogelio.turnosnominalft.modelo.TipoJornada;

/**
 *
 * @author rogelio_sld
 */
public class TurnosNominaLft {

    public static void main(String[] args) {
        Trabajador trabajador = new Trabajador ("Rogelio Saldaña", new BigDecimal ("600.99"));
        System.out.println(" Nombre : " + trabajador.getNombre() + " Salario Diario : " + trabajador.getSalarioDiario() );
        System.out.println(" Limite de minutos en Diurna : " + TipoJornada.DIURNA.getLimiteDiarioMinutos());
        System.out.println(" Limite de minutos en Nocturno : " + TipoJornada.NOCTURNO.getLimiteDiarioMinutos());
        System.out.println(" Limite de minutos en Mixto : " + TipoJornada.MIXTO.getLimiteDiarioMinutos());
    }
}
