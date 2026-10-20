package com.rogelio.turnosnominalft.ui;

import com.rogelio.turnosnominalft.calculo.CalculadoraSemanal;
import com.rogelio.turnosnominalft.modelo.DiaTrabajado;
import com.rogelio.turnosnominalft.modelo.ResultadoSemana;
import com.rogelio.turnosnominalft.modelo.SemanaTrabajada;
import com.rogelio.turnosnominalft.modelo.Trabajador;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * El "mesero" en consola: pregunta los datos, llama a la calculadora e imprime el desglose.
 * Es la unica clase (junto con la ventana) que lee del teclado y escribe en pantalla.
 */
public class ConsolaUI {

    private final Scanner teclado = new Scanner(System.in);

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== TURNOS Y NOMINA (LFT) ===");
            System.out.println("1. Calcular una semana");
            System.out.println("2. Abrir ventana grafica");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            String opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1" -> calcularSemana();
                case "2" -> VentanaPrincipal.abrir();
                case "0" -> salir = true;
                default -> System.out.println("Opcion no valida.");
            }
        }
        System.out.println("Hasta luego.");
    }

    private void calcularSemana() {
        try {
            String nombre = pedirTexto("Nombre del trabajador: ");
            BigDecimal salario = pedirDecimal("Salario diario (ej. 520.00): ");
            Trabajador trabajador = new Trabajador(nombre, salario);

            LocalDate inicio = pedirFecha("Fecha del primer dia de la semana (AAAA-MM-DD): ");

            List<DiaTrabajado> dias = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                LocalDate fecha = inicio.plusDays(i);
                String nombreDia = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-MX"));
                System.out.println("\n--- " + nombreDia + " " + fecha + " ---");
                dias.add(pedirDia());
            }

            SemanaTrabajada semana = new SemanaTrabajada(inicio, dias);
            ResultadoSemana resultado = CalculadoraSemanal.calcular(trabajador, semana);
            System.out.println("\n" + FormateadorDesglose.formatear(resultado));

        } catch (IllegalArgumentException e) {
            // Aqui caen los errores de validacion del modelo y del validador
            System.out.println(e.getMessage());
        }
    }

    // Regresa null si ese dia no se trabajo (Enter en la entrada)
    private DiaTrabajado pedirDia() {
        LocalTime entrada = pedirHora("Hora de entrada HH:mm (Enter si no trabajo): ", true);
        if (entrada == null) {
            return null;
        }
        LocalTime salida = pedirHora("Hora de salida HH:mm: ", false);
        int descanso = pedirEntero("Minutos de descanso (0 si no hubo): ");
        return new DiaTrabajado(entrada, salida, descanso);
    }

    private String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    private BigDecimal pedirDecimal(String mensaje) {
        while (true) {
            try {
                return new BigDecimal(pedirTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero valido, por ejemplo 520.00");
            }
        }
    }

    private LocalDate pedirFecha(String mensaje) {
        while (true) {
            try {
                return LocalDate.parse(pedirTexto(mensaje));
            } catch (DateTimeParseException e) {
                System.out.println("Formato incorrecto. Usa AAAA-MM-DD, por ejemplo 2026-10-12");
            }
        }
    }

    private LocalTime pedirHora(String mensaje, boolean permiteVacio) {
        while (true) {
            String texto = pedirTexto(mensaje);
            if (permiteVacio && texto.isEmpty()) {
                return null;
            }
            try {
                return LocalTime.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("Formato incorrecto. Usa HH:mm en 24 horas, por ejemplo 09:00 o 22:30");
            }
        }
    }

    private int pedirEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(pedirTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero entero, por ejemplo 30");
            }
        }
    }
}
