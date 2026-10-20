package com.rogelio.turnosnominalft.calculo;

import com.rogelio.turnosnominalft.modelo.DiaTrabajado;
import com.rogelio.turnosnominalft.modelo.ResultadoDia;
import com.rogelio.turnosnominalft.modelo.ResultadoSemana;
import com.rogelio.turnosnominalft.modelo.SemanaTrabajada;
import com.rogelio.turnosnominalft.modelo.TipoJornada;
import com.rogelio.turnosnominalft.modelo.Trabajador;
import com.rogelio.turnosnominalft.reglas.ConfigBono;
import com.rogelio.turnosnominalft.reglas.ReglasVigentes;
import com.rogelio.turnosnominalft.reglas.TablaReglas;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * La "cocina": recibe un trabajador y una semana, y regresa el resultado.
 * No imprime nada ni lee el teclado, y no trae numeros de la ley: los pide a reglas.
 */
public class CalculadoraSemanal {

    public static ResultadoSemana calcular(Trabajador trabajador, SemanaTrabajada semana) {
        ValidadorSemana.validar(semana);

        TablaReglas reglas = ReglasVigentes.paraAnio(semana.getFechaInicio().getYear());

        List<ResultadoDia> resultados = new ArrayList<>();
        List<String> advertencias = new ArrayList<>();

        int doblesRestantes = reglas.getMaxDoblesMin();  
        int triplesRestantes = reglas.getMaxTriplesMin(); 
        int diasConExtra = 0;
        int ordinariosSemana = 0;

        for (int i = 0; i < 7; i++) {
            DiaTrabajado dia = semana.getDias().get(i);
            if (dia == null) {
                continue; // ese dia no se trabajo
            }

            LocalDate fecha = semana.getFechaInicio().plusDays(i);
            TipoJornada tipo = ClasificadorJornada.clasificar(dia);
            int limiteDia = tipo.getLimiteDiarioMinutos();
            int efectivos = dia.minutosEfectivos();

            // 1) Lo que cabe en la jornada normal del dia (art. 61)
            int ordinarios = Math.min(efectivos, limiteDia);
            int resto = efectivos - ordinarios;

            // 2) Lo que se puede considerar extra ese dia
            int extra = Math.min(resto, reglas.getMaxExtraDiaMin());
            extra = Math.min(extra, reglas.getMaxTotalDiaMin() - ordinarios);
            extra = Math.max(extra, 0);
            int fuera = resto - extra; 

            // 3) Tope de dias con extra en la semana 
            if (extra > 0) {
                if (diasConExtra >= reglas.getMaxDiasConExtra()) {
                    fuera += extra;
                    extra = 0;
                } else {
                    diasConExtra++;
                }
            }

            // 4) Reparto del extra
            int dobles = Math.min(extra, doblesRestantes);
            doblesRestantes -= dobles;
            int sobrante = extra - dobles;
            int triples = Math.min(sobrante, triplesRestantes);
            triplesRestantes -= triples;
            fuera += sobrante - triples; 

            // 5) Dinero. Valor de la hora = salario diario / horas de la jornada 
            BigDecimal salario = trabajador.getSalarioDiario();
            BigDecimal valorHora = salario.multiply(BigDecimal.valueOf(60))
                    .divide(BigDecimal.valueOf(limiteDia), 2, RoundingMode.HALF_UP);
            BigDecimal pagoDobles = pagar(salario, dobles, 2, limiteDia);   // 100% mas = x2
            BigDecimal pagoTriples = pagar(salario, triples, 3, limiteDia); // 200% mas = x3
            BigDecimal bono = ConfigBono.bonoPara(tipo);

            resultados.add(new ResultadoDia(fecha, tipo, efectivos, ordinarios, dobles, triples, fuera,
                    valorHora, pagoDobles, pagoTriples, bono));

            ordinariosSemana += ordinarios;
            if (fuera > 0) {
                advertencias.add(fecha + ": " + fuera + " min fuera de tope (arts. 66 y 68), requiere revision.");
            }
        }

        // Aviso no cambia el pag: jornada ordinaria semanal arriba del maximo del año basanodme en el 59 
        if (ordinariosSemana > reglas.getMaxSemanalOrdinarioMin()) {
            advertencias.add("La jornada ordinaria semanal (" + ordinariosSemana / 60.0 + " h) supera el maximo de "
                    + reglas.getMaxSemanalOrdinarioMin() / 60 + " h para " + reglas.getAnio() + " (art. 59), requiere revision.");
        }

        return new ResultadoSemana(trabajador, semana.getFechaInicio(), reglas.getAnio(), resultados, advertencias);
    }

    
    private static BigDecimal pagar(BigDecimal salario, int minutos, int factor, int limiteDiaMin) {
        return salario.multiply(BigDecimal.valueOf((long) minutos * factor))
                .divide(BigDecimal.valueOf(limiteDiaMin), 2, RoundingMode.HALF_UP);
    }
}
