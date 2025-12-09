package vista;

import dao.ClienteDAO;
import dao.ProductoDAO;
import dao.VentaDAO;
import modelo.Cliente;
import modelo.DetalleVenta;
import modelo.Producto;
import modelo.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioNuevaVenta extends JDialog {

    private JComboBox<Cliente> cmbClientes;
    private JComboBox<Producto> cmbProductos;
    private JTextField txtCantidad;
    private JTable tablaDetalles;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;
    private JButton btnAgregar, btnTerminar;

    private List<DetalleVenta> listaDetalles; // El "Carrito" en memoria
    private double totalVenta = 0.0;
    private boolean ventaExitosa = false;

    public FormularioNuevaVenta(Frame owner) {
        super(owner, "Nueva Venta", true);
        setSize(800, 600);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        listaDetalles = new ArrayList<>();

        // --- PANEL SUPERIOR: Selección de datos ---
        JPanel panelSuperior = new JPanel(new GridLayout(3, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos de la Venta"));

        // Cargar Clientes
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        cmbClientes = new JComboBox<>(clientes.toArray(new Cliente[0])); // Requiere toString() en Cliente

        // Cargar Productos (Modificamos Producto para tener toString útil)
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.obtenerTodos();
        cmbProductos = new JComboBox<>(productos.toArray(new Producto[0]));

        txtCantidad = new JTextField("1");

        panelSuperior.add(new JLabel("Cliente:"));
        panelSuperior.add(cmbClientes);
        panelSuperior.add(new JLabel("Producto:"));
        panelSuperior.add(cmbProductos);
        panelSuperior.add(new JLabel("Cantidad:"));
        panelSuperior.add(txtCantidad);

        add(panelSuperior, BorderLayout.NORTH);

        // --- PANEL CENTRAL: Tabla (Carrito) ---
        String[] columnas = {"Producto", "Precio Unit.", "Cantidad", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaDetalles = new JTable(modeloTabla);
        add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);

        // --- PANEL INFERIOR: Botones y Total ---
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar al Carrito (+)");
        btnTerminar = new JButton("Finalizar Venta");
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setForeground(Color.BLUE);

        panelInferior.add(btnAgregar);
        panelInferior.add(Box.createHorizontalStrut(20));
        panelInferior.add(lblTotal);
        panelInferior.add(Box.createHorizontalStrut(20));
        panelInferior.add(btnTerminar);

        add(panelInferior, BorderLayout.SOUTH);

        // --- ACCIONES ---

        // Botón Agregar Producto
        btnAgregar.addActionListener(e -> {
            agregarProductoAlCarrito();
        });

        // Botón Terminar Venta
        btnTerminar.addActionListener(e -> {
            guardarVentaEnBD();
        });
    }

    private void agregarProductoAlCarrito() {
        try {
            Producto p = (Producto) cmbProductos.getSelectedItem();
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (p == null) return;
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
                return;
            }
            if (cantidad > p.getStock()) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Solo quedan: " + p.getStock());
                return;
            }

            // Crear detalle y sumarlo a la lista
            DetalleVenta detalle = new DetalleVenta(p.getId(), p.getNombre(), cantidad, p.getPrecio());
            listaDetalles.add(detalle);

            // Actualizar tabla visual
            modeloTabla.addRow(new Object[]{
                    p.getNombre(),
                    p.getPrecio(),
                    cantidad,
                    detalle.getSubtotal()
            });

            // Actualizar Total
            totalVenta += detalle.getSubtotal();
            lblTotal.setText("Total: $" + String.format("%.2f", totalVenta));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Escribe un número válido en cantidad.");
        }
    }

    private void guardarVentaEnBD() {
        if (listaDetalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        Cliente cliente = (Cliente) cmbClientes.getSelectedItem();

        Venta venta = new Venta();
        venta.setClienteId(cliente.getId());
        venta.setTotal(totalVenta);

        VentaDAO dao = new VentaDAO();
        if (dao.registrarVenta(venta, listaDetalles)) {
            JOptionPane.showMessageDialog(this, "¡Venta registrada con éxito!");
            ventaExitosa = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la venta en la base de datos.");
        }
    }

    public boolean isVentaExitosa() { return ventaExitosa; }
}
