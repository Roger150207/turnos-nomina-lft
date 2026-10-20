package com.rogelio.turnosnominalft.ui;

import com.rogelio.turnosnominalft.modelo.ResultadoDia;
import com.rogelio.turnosnominalft.modelo.ResultadoSemana;
import java.math.BigDecimal;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Convierte un ResultadoSemana en el texto del desglose.
 * Solo REGRESA texto: quien lo imprime es la consola o la ventana.
 */
public class FormateadorDesglose {

    private static final String LINEA = "============================================================";
    private static final String RAYA = "------------------------------------------------------------";

    public static String formatear(ResultadoSemana r) {
        StringBuilder sb = new StringBuilder();

        sb.append(LINEA).append("\n");
        sb.append(" DESGLOSE SEMANAL (LFT, reforma DOF 1-may-2026)\n");
        sb.append(LINEA).append("\n");
        sb.append(" Trabajador     : ").append(r.getTrabajador().getNombre()).append("\n");
        sb.append(" Salario diario : ").append(dinero(r.getTrabajador().getSalarioDiario())).append("\n");
        sb.append(" Semana         : ").append(r.getFechaInicio()).append(" al ")
                .append(r.getFechaInicio().plusDays(6)).append("\n");
        sb.append(" Reglas del anio: ").append(r.getAnioReglas()).append("\n");
        sb.append(RAYA).append("\n");
        sb.append(" DETALLE POR DIA\n");

        for (ResultadoDia d : r.getDias()) {
            String nombreDia = d.getFecha().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("es-MX"));
            sb.append("\n ").append(nombreDia).append(" ").append(d.getFecha())
                    .append(" | ").append(d.getTipo())
                    .append(" | trabajado ").append(horas(d.getMinutosEfectivos()))
                    .append(" | valor hora ").append(dinero(d.getValorHora())).append("\n");
            sb.append("     ordinarias ........ ").append(horas(d.getMinutosOrdinarios()))
                    .append("  (art. 61)\n");
            if (d.getMinutosDobles() > 0) {
                sb.append("     extra doble ....... ").append(horas(d.getMinutosDobles()))
                        .append(" -> ").append(dinero(d.getPagoDobles())).append("  (art. 66, 100% mas)\n");
            }
            if (d.getMinutosTriples() > 0) {
                sb.append("     extra triple ...... ").append(horas(d.getMinutosTriples()))
                        .append(" -> ").append(dinero(d.getPagoTriples())).append("  (art. 68, 200% mas)\n");
            }
            if (d.getMinutosFueraDeTope() > 0) {
                sb.append("     FUERA DE TOPE ..... ").append(horas(d.getMinutosFueraDeTope()))
                        .append("  no se paga aqui, requiere revision\n");
            }
            if (d.getBono().compareTo(BigDecimal.ZERO) > 0) {
                sb.append("     bono de turno ..... ").append(dinero(d.getBono()))
                        .append("  (politica de la empresa)\n");
            }
        }

        sb.append("\n").append(RAYA).append("\n");
        sb.append(" RESUMEN DE LA SEMANA\n");
        sb.append(" Extra doble  (art. 66) : ").append(horas(r.getMinutosDobles()))
                .append(" -> ").append(dinero(r.getPagoDobles())).append("\n");
        sb.append(" Extra triple (art. 68) : ").append(horas(r.getMinutosTriples()))
                .append(" -> ").append(dinero(r.getPagoTriples())).append("\n");
        sb.append(" Bono de turno          : ").append(dinero(r.getBono())).append("\n");
        sb.append(" TOTAL (extras + bono)  : ").append(dinero(r.getTotal())).append("\n");
        sb.append(" (No incluye el salario ordinario de la semana)\n");

        if (r.getMinutosFueraDeTope() > 0 || !r.getAdvertencias().isEmpty()) {
            sb.append(RAYA).append("\n");
            sb.append(" ADVERTENCIAS\n");
            for (String a : r.getAdvertencias()) {
                sb.append("  - ").append(a).append("\n");
            }
        }
        sb.append(LINEA).append("\n");
        return sb.toString();
    }

    private static String horas(int minutos) {
        return (minutos / 60) + " h " + String.format("%02d", minutos % 60) + " min";
    }

    private static String dinero(BigDecimal monto) {
        return String.format(Locale.US, "$%,.2f", monto);
    }
}
