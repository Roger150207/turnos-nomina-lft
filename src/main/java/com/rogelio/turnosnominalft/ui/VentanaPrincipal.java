package com.rogelio.turnosnominalft.ui;

import com.rogelio.turnosnominalft.calculo.CalculadoraSemanal;
import com.rogelio.turnosnominalft.modelo.DiaTrabajado;
import com.rogelio.turnosnominalft.modelo.ResultadoSemana;
import com.rogelio.turnosnominalft.modelo.SemanaTrabajada;
import com.rogelio.turnosnominalft.modelo.Trabajador;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * El "mesero" en ventana (Swing). Usa la MISMA calculadora y el MISMO formateador
 * que la consola: aqui no hay ninguna regla de la ley.
 * Un dia con la entrada vacia se toma como "no trabajo".
 */
public class VentanaPrincipal extends JFrame {

    private final JTextField campoNombre = new JTextField(16);
    private final JTextField campoSalario = new JTextField("520.00", 8);
    private final JTextField campoFecha = new JTextField("2026-10-12", 10);

    // La primera columna (Dia) no se puede editar
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Dia", "Entrada (HH:mm)", "Salida (HH:mm)", "Descanso (min)"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return columna != 0;
        }
    };
    private final JTable tabla = new JTable(modelo);
    private final JTextArea areaResultado = new JTextArea(22, 64);

    public VentanaPrincipal() {
        setTitle("Turnos y Nomina (LFT)");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        for (int i = 1; i <= 7; i++) {
            modelo.addRow(new Object[]{"Dia " + i, "", "", "0"});
        }

        // Parte de arriba: nombre, salario y fecha
        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Salario diario:"));
        formulario.add(campoSalario);
        formulario.add(new JLabel("Primer dia (AAAA-MM-DD):"));
        formulario.add(campoFecha);

        // Centro: tabla de 7 dias + boton
        tabla.setPreferredScrollableViewportSize(new Dimension(520, 130));
        JButton botonCalcular = new JButton("Calcular");
        botonCalcular.addActionListener(e -> calcular());

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelTabla.add(botonCalcular, BorderLayout.SOUTH);

        // Abajo: resultado
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
        centro.add(panelTabla, BorderLayout.NORTH);
        centro.add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(formulario, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    // Abre la ventana (Swing pide hacerlo con invokeLater)
    public static void abrir() {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }

    private void calcular() {
        // Si el usuario sigue escribiendo en una celda, la confirmo primero
        if (tabla.isEditing()) {
            tabla.getCellEditor().stopCellEditing();
        }

        try {
            Trabajador trabajador = new Trabajador(
                    campoNombre.getText().trim(), new BigDecimal(campoSalario.getText().trim()));
            LocalDate inicio = LocalDate.parse(campoFecha.getText().trim());

            List<DiaTrabajado> dias = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                String entrada = celda(i, 1);
                if (entrada.isEmpty()) {
                    dias.add(null); // dia sin trabajar
                } else {
                    dias.add(new DiaTrabajado(
                            LocalTime.parse(entrada), LocalTime.parse(celda(i, 2)), Integer.parseInt(celda(i, 3))));
                }
            }

            SemanaTrabajada semana = new SemanaTrabajada(inicio, dias);
            ResultadoSemana resultado = CalculadoraSemanal.calcular(trabajador, semana);
            areaResultado.setText(FormateadorDesglose.formatear(resultado));
            areaResultado.setCaretPosition(0);

        } catch (DateTimeParseException e) {
            areaResultado.setText("Revisa el formato: fecha AAAA-MM-DD y horas HH:mm en 24 horas.");
        } catch (NumberFormatException e) {
            areaResultado.setText("Revisa los numeros: salario (ej. 520.00) y descanso (minutos enteros).");
        } catch (IllegalArgumentException e) {
            areaResultado.setText(e.getMessage());
        }
    }

    // Lee el texto de una celda de la tabla
    private String celda(int fila, int columna) {
        Object valor = modelo.getValueAt(fila, columna);
        return valor == null ? "" : valor.toString().trim();
    }

    // Para probarla sola desde NetBeans 
    public static void main(String[] args) {
        abrir();
    }
}
