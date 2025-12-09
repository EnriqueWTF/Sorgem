package vista;

import dao.VentaDAO;
import modelo.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVentas extends JDialog {

    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private VentaDAO ventaDAO;

    public VentanaVentas(JFrame owner) {
        super(owner, "Historial de Ventas", true);
        setSize(800, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        ventaDAO = new VentaDAO();

        // Modelo de la tabla
        String[] columnas = {"ID Venta", "Cliente", "Fecha", "Total ($)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnNuevaVenta = new JButton("Nueva Venta");
        JButton btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnNuevaVenta);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos
        cargarVentas();

        // --- Listeners ---
        btnCerrar.addActionListener(e -> dispose());

        btnNuevaVenta.addActionListener(e -> {
            FormularioNuevaVenta form = new FormularioNuevaVenta((JFrame) getOwner());
            form.setVisible(true);

            // Si se hizo la venta, recargar la tabla
            if (form.isVentaExitosa()) {
                cargarVentas();
            }
        });
    }

    private void cargarVentas() {
        modeloTabla.setRowCount(0);
        List<Venta> ventas = ventaDAO.obtenerTodas();
        for (Venta v : ventas) {
            Object[] fila = {
                    v.getId(),
                    v.getNombreCliente(), // Muestra el nombre, no el ID
                    v.getFecha(),
                    String.format("%.2f", v.getTotal()) // Formato de moneda
            };
            modeloTabla.addRow(fila);
        }
    }
}
