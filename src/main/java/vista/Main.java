package vista;
import vista.VentanaClientes;
import javax.swing.*;
import java.awt.*;

/**
 * Clase principal que representa la ventana principal de la aplicación Sorgem.
 */
public class Main extends JFrame {

    public Main() {
        // Configuración básica de la ventana
        setTitle("Sorgem - Panel Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setLayout(new BorderLayout()); // Usaremos BorderLayout para organizar los paneles

        // =========================
        // PANEL SUPERIOR (Encabezado)
        // =========================
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 204)); // Azul oscuro
        JLabel titleLabel = new JLabel("SORGEM - Sistema de Gestión Inteligente");
        titleLabel.setForeground(Color.WHITE); // Texto en blanco
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Fuente elegante y legible
        topPanel.add(titleLabel);

        // =========================
        // PANEL CENTRAL (Botones de acción)
        // =========================
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 15, 15)); // 2 filas x 2 columnas con espacio entre botones
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno

        // Crear botones
        JButton btnVentas = new JButton("Ventas");
        JButton btnProductos = new JButton("Productos");
        JButton btnClientes = new JButton("Clientes");
        JButton btnPredicciones = new JButton("Predicciones IA");

        // Personalizar estilo de los botones
        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        Color btnColor = new Color(51, 153, 255); // Azul
        JButton[] botones = {btnVentas, btnProductos, btnClientes, btnPredicciones};

        for (JButton boton : botones) {
            boton.setFont(btnFont);
            boton.setBackground(btnColor);
            boton.setForeground(Color.WHITE);
            boton.setFocusPainted(false); // Quitar borde cuando se selecciona
            boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            centerPanel.add(boton); // Agregar botón al panel central
        }

        // =========================
        // ACCIONES DE LOS BOTONES
        // =========================

        // Abrir ventana de Productos
        btnProductos.addActionListener(e -> {
            VentanaProductos ventana = new VentanaProductos(this); // 'this' se refiere a la ventana principal
            ventana.setVisible(true);
        });

        // Abrir ventana de Clientes
        btnClientes.addActionListener(e -> {
            VentanaClientes ventana = new VentanaClientes(this);
            ventana.setVisible(true);
        });

        // Mostrar mensaje temporal para el módulo IA
        btnPredicciones.addActionListener(e -> {
            VentanaIA ventana = new VentanaIA(this);
            ventana.setVisible(true);
        });

        btnVentas.addActionListener(e -> {
            VentanaVentas ventana = new VentanaVentas(this);
            ventana.setVisible(true);
        });

        // =========================
        // AGREGAR PANELES A LA VENTANA
        // =========================
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Ejecutar la ventana en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
