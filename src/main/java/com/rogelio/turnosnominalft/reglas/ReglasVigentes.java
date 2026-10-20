package com.rogelio.turnosnominalft.reglas;

/**
 * Entrega la TablaReglas que corresponde a un anio.
 * Antes de 2026 no aplica la reforma; despues de 2030 se usan los valores de 2030.
 */
public class ReglasVigentes {

    public static TablaReglas paraAnio(int anio) {
        if (anio < 2026) {
            throw new IllegalArgumentException(" ERROR : Antes de 2026 no aplican estas reglas ");
        }

        // Aqui aplico la ley del 2030 en adelante se queda igual que 2030
        int a = Math.min(anio, 2030);

        // año, ordinarias/semana, dobles/semana, triples/semana, extra/dia, total/dia, dias con extra
        return switch (a) {
            case 2026 -> new TablaReglas(2026, 48 * 60, 9 * 60, 4 * 60, 4 * 60, 12 * 60, 4);
            case 2027 -> new TablaReglas(2027, 46 * 60, 9 * 60, 4 * 60, 4 * 60, 12 * 60, 4);
            case 2028 -> new TablaReglas(2028, 44 * 60, 10 * 60, 4 * 60, 4 * 60, 12 * 60, 4);
            case 2029 -> new TablaReglas(2029, 42 * 60, 11 * 60, 4 * 60, 4 * 60, 12 * 60, 4);
            default -> new TablaReglas(2030, 40 * 60, 12 * 60, 4 * 60, 4 * 60, 12 * 60, 4);
        };
    }
}
