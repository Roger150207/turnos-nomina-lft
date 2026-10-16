package com.rogelio.turnosnominalft;

import com.rogelio.turnosnominalft.modelo.Trabajador;
import java.math.BigDecimal;
/**
 *
 * @author rogelio_sld
 */
public class TurnosNominaLft {

    public static void main(String[] args) {
        Trabajador trabajador = new Trabajador ("Rogelio Saldaña", new BigDecimal ("600.99"));
        System.out.println(" Nombre : " + trabajador.getNombre() + " Salario Diario : " + trabajador.getSalarioDiario() );
    }
}
