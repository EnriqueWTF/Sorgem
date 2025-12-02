
package vista;

import dao.ProductoDAO;
import dao.VentaDAO;
import modelo.Producto;
import modelo.Venta;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class VentanaVentas extends JDialog {

    private JComboBox<Producto> cmbProductos;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JCheckBox chkPromocion;
    private JButton btnRegistrar;
    private ProductoDAO productoDAO;
    private VentaDAO ventaDAO;

    public VentanaVentas(JFrame owner) {
        super(owner, "Registrar Nueva Venta", true);
        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        productoDAO = new ProductoDAO();
        ventaDAO = new VentaDAO();

        // --- Panel Formulario ---
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Selector de Producto
        panelFormulario.add(new JLabel("Producto:"));
        cmbProductos = new JComboBox<>();
        cargarProductos(); // Llenar el combo con datos de la BD
        panelFormulario.add(cmbProductos);

        // 2. Precio (Se llena solo al elegir producto)
        panelFormulario.add(new JLabel("Precio Unitario:"));
        txtPrecio = new JTextField();
        txtPrecio.setEditable(false); // No dejar que lo cambien manual
        panelFormulario.add(txtPrecio);

        // Evento: Al cambiar producto, actualizar precio
        cmbProductos.addActionListener(e -> actualizarPrecio());

        // 3. Cantidad
        panelFormulario.add(new JLabel("Cantidad Vendida:"));
        txtCantidad = new JTextField();
        panelFormulario.add(txtCantidad);

        // 4. ¿Es Promoción?
        panelFormulario.add(new JLabel("¿Está en Promoción?"));
        chkPromocion = new JCheckBox("Sí");
        panelFormulario.add(chkPromocion);

        add(panelFormulario, BorderLayout.CENTER);

        // --- Botón Registrar ---
        btnRegistrar = new JButton("REGISTRAR VENTA");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBackground(new Color(0, 153, 76)); // Verde
        btnRegistrar.setForeground(Color.WHITE);
        
        btnRegistrar.addActionListener(e -> registrarVenta());
        
        add(btnRegistrar, BorderLayout.SOUTH);

        // Inicializar precio del primer elemento
        actualizarPrecio(); 
    }

    private void cargarProductos() {
        List<Producto> productos = productoDAO.obtenerTodos();
        for (Producto p : productos) {
            cmbProductos.addItem(p);
        }
    }

    private void actualizarPrecio() {
        Producto p = (Producto) cmbProductos.getSelectedItem();
        if (p != null) {
            txtPrecio.setText(String.valueOf(p.getPrecio()));
        }
    }

    private void registrarVenta() {
        try {
            // 1. Obtener datos del formulario
            Producto prodSeleccionado = (Producto) cmbProductos.getSelectedItem();
            if (prodSeleccionado == null) return;

            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = prodSeleccionado.getPrecio();
            int esPromocion = chkPromocion.isSelected() ? 1 : 0; // 1 si es true, 0 si es false

            // 2. Obtener fecha actual automáticamente
            LocalDate fechaHoy = LocalDate.now();
            int anio = fechaHoy.getYear();
            int mes = fechaHoy.getMonthValue();

            Venta nuevaVenta = new Venta(anio, mes, precio, esPromocion, cantidad);

            // 4. Guardar en Base de Datos
            if (ventaDAO.registrarVenta(nuevaVenta)) {
                JOptionPane.showMessageDialog(this, "¡Venta registrada correctamente!");
                dispose(); // Cerrar ventana
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}