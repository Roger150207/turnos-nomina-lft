package com.rogelio.turnosnominalft.calculo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rogelio.turnosnominalft.modelo.DiaTrabajado;
import com.rogelio.turnosnominalft.modelo.ResultadoSemana;
import com.rogelio.turnosnominalft.modelo.SemanaTrabajada;
import com.rogelio.turnosnominalft.modelo.TipoJornada;
import com.rogelio.turnosnominalft.modelo.Trabajador;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Un @Test por cada caso de docs/casos-de-prueba.md.
 * Los valores esperados salen de calculos hechos A MANO (no del programa).
 */
class CalculadoraSemanalTest {

    private static final Trabajador TRABAJADOR = new Trabajador("Prueba", new BigDecimal("520.00"));

    private static DiaTrabajado dia(String entrada, String salida, int descanso) {
        return new DiaTrabajado(LocalTime.parse(entrada), LocalTime.parse(salida), descanso);
    }

    private static SemanaTrabajada semana(LocalDate inicio, DiaTrabajado... dias) {
        return new SemanaTrabajada(inicio, Arrays.asList(dias));
    }

    // Compara dinero con compareTo (equals de BigDecimal tambien compara la escala: 1755 != 1755.00)
    private static void assertDinero(String esperado, BigDecimal real) {
        assertEquals(0, new BigDecimal(esperado).compareTo(real), "esperado " + esperado + " pero fue " + real);
    }

    // 11 h trabajadas (08:00 a 19:30 con 30 min de descanso) y 8 h (08:00 a 16:30)
    private static final DiaTrabajado DIA_11H = dia("08:00", "19:30", 30);
    private static final DiaTrabajado DIA_8H = dia("08:00", "16:30", 30);

    @Test
    void caso1_2026_nueveDoblesTresTriples() {
        SemanaTrabajada s = semana(LocalDate.of(2026, 10, 12),
                DIA_11H, DIA_11H, DIA_11H, DIA_11H, DIA_8H, null, null);
        ResultadoSemana r = CalculadoraSemanal.calcular(TRABAJADOR, s);

        assertEquals(540, r.getMinutosDobles());   // 9 h
        assertEquals(180, r.getMinutosTriples());  // 3 h
        assertEquals(0, r.getMinutosFueraDeTope());
        assertDinero("1170.00", r.getPagoDobles()); // 9 x 130
        assertDinero("585.00", r.getPagoTriples()); // 3 x 195
        assertDinero("1755.00", r.getTotal());
    }

    @Test
    void caso2_2028_diezDoblesDosTriples() {
        SemanaTrabajada s = semana(LocalDate.of(2028, 10, 16),
                DIA_11H, DIA_11H, DIA_11H, DIA_11H, DIA_8H, null, null);
        ResultadoSemana r = CalculadoraSemanal.calcular(TRABAJADOR, s);

        assertEquals(600, r.getMinutosDobles());   // 10 h
        assertEquals(120, r.getMinutosTriples());  // 2 h
        assertDinero("1300.00", r.getPagoDobles()); // 10 x 130
        assertDinero("390.00", r.getPagoTriples()); // 2 x 195
        assertDinero("1690.00", r.getTotal());
    }

    @Test
    void caso3_lunesDe13Horas_unaHoraFueraDeTope() {
        // 06:00 a 19:30 con 30 min de descanso = 13 h trabajadas
        SemanaTrabajada s = semana(LocalDate.of(2026, 10, 12),
                dia("06:00", "19:30", 30), null, null, null, null, null, null);
        ResultadoSemana r = CalculadoraSemanal.calcular(TRABAJADOR, s);

        assertEquals(240, r.getMinutosDobles());       // 4 h
        assertEquals(0, r.getMinutosTriples());
        assertEquals(60, r.getMinutosFueraDeTope());   // la hora 13
        assertDinero("520.00", r.getTotal());          // 4 x 130
    }

    @Test
    void caso4_semanaSinExtras() {
        DiaTrabajado normal = dia("09:00", "17:30", 30); // 8 h exactas
        SemanaTrabajada s = semana(LocalDate.of(2026, 10, 12),
                normal, normal, normal, normal, normal, null, null);
        ResultadoSemana r = CalculadoraSemanal.calcular(TRABAJADOR, s);

        assertEquals(0, r.getMinutosDobles());
        assertEquals(0, r.getMinutosTriples());
        assertDinero("0.00", r.getTotal());
    }

    @Test
    void caso5_turnoNocturnoQueCruzaMedianoche() {
        // 22:00 a 06:00 con 30 min = 450 min; limite nocturno 420 -> 30 min de extra doble
        SemanaTrabajada s = semana(LocalDate.of(2026, 10, 12),
                dia("22:00", "06:00", 30), null, null, null, null, null, null);
        ResultadoSemana r = CalculadoraSemanal.calcular(TRABAJADOR, s);

        assertEquals(TipoJornada.NOCTURNA, r.getDias().get(0).getTipo());
        assertEquals(30, r.getMinutosDobles());
        assertDinero("74.29", r.getPagoDobles()); // 520 x 30 x 2 / 420
        assertDinero("150.00", r.getBono());
        assertDinero("224.29", r.getTotal());
    }

    @Test
    void caso6_turnoMixto() {
        // 14:00 a 22:00 sin descanso: 2 h de noche (menos de 3.5 h) -> MIXTA, limite 450
        SemanaTrabajada s = semana(LocalDate.of(2026, 10, 12),
                dia("14:00", "22:00", 0), null, null, null, null, null, null);
        ResultadoSemana r = CalculadoraSemanal.calcular(TRABAJADOR, s);

        assertEquals(TipoJornada.MIXTA, r.getDias().get(0).getTipo());
        assertEquals(30, r.getMinutosDobles());
        assertDinero("69.33", r.getPagoDobles()); // 520 x 30 x 2 / 450
        assertDinero("75.00", r.getBono());
        assertDinero("144.33", r.getTotal());
    }

    @Test
    void caso7_fechaAnteriorALaReformaSeRechaza() {
        assertThrows(IllegalArgumentException.class, () ->
                semana(LocalDate.of(2026, 3, 2), DIA_8H, null, null, null, null, null, null));
    }

    @Test
    void caso8_masDeCuatroDiasConExtra() {
        // Lunes a sabado de 10 h: 2 h extra cada dia, pero solo 4 dias pueden llevar extra
        DiaTrabajado diez = dia("08:00", "18:30", 30);
        SemanaTrabajada s = semana(LocalDate.of(2026, 10, 12),
                diez, diez, diez, diez, diez, diez, null);
        ResultadoSemana r = CalculadoraSemanal.calcular(TRABAJADOR, s);

        assertEquals(480, r.getMinutosDobles());      // 4 dias x 2 h
        assertEquals(240, r.getMinutosFueraDeTope()); // dias 5 y 6 x 2 h
        assertDinero("1040.00", r.getTotal());        // 8 x 130
    }
}
