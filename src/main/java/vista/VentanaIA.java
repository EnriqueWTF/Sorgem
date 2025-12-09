package vista;

import dao.PrediccionDAO;
import modelo.PrediccionIA;

import javax.swing.*;
import java.awt.*;

public class VentanaIA extends JDialog {

    private PrediccionDAO prediccionDAO;

    public VentanaIA(JFrame owner) {
        super(owner, "Módulo de Inteligencia Artificial", true);
        setSize(500, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        prediccionDAO = new PrediccionDAO();

        // 1. Título
        JLabel lblTitulo = new JLabel("Análisis Predictivo de Ventas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        lblTitulo.setForeground(new Color(0, 102, 204)); // Azul
        add(lblTitulo, BorderLayout.NORTH);

        // 2. Panel Central (Resultados)
        JPanel panelResultados = new JPanel(new GridLayout(4, 1, 10, 10));
        panelResultados.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Obtenemos los datos de la BD
        PrediccionIA datos = prediccionDAO.obtenerUltimaPrediccion();

        if (datos != null) {
            JLabel lblMes = new JLabel("📅 Mes Pico Estimado: " + obtenerNombreMes(datos.getMejorMes()));
            lblMes.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JLabel lblCantidad = new JLabel("📈 Ventas Proyectadas: " + datos.getCantidadEstimada() + " unidades");
            lblCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JTextArea txtMensaje = new JTextArea("💡 " + datos.getMensaje());
            txtMensaje.setLineWrap(true);
            txtMensaje.setWrapStyleWord(true);
            txtMensaje.setEditable(false);
            txtMensaje.setBackground(new Color(240, 240, 240));
            txtMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 14));

            JLabel lblFecha = new JLabel("Última actualización: " + datos.getFechaCalculo());
            lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            lblFecha.setForeground(Color.GRAY);

            panelResultados.add(lblMes);
            panelResultados.add(lblCantidad);
            panelResultados.add(txtMensaje);
            panelResultados.add(lblFecha);
        } else {
            JLabel lblError = new JLabel("⚠️ No hay predicciones disponibles.");
            JLabel lblInfo = new JLabel("Ejecuta el script de Python primero.");
            lblError.setForeground(Color.RED);
            panelResultados.add(lblError);
            panelResultados.add(lblInfo);
        }

        add(panelResultados, BorderLayout.CENTER);

        // 3. Botón Cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private String obtenerNombreMes(int numeroMes) {
        String[] meses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        if (numeroMes >= 1 && numeroMes <= 12) return meses[numeroMes];
        return "Desconocido";
    }
}