package vista;

import dao.ProductoDAO;
import modelo.Producto;

import javax.swing.*;
import java.awt.*;

public class FormularioProductos extends JDialog {

    private JTextField txtNombre, txtDescripcion, txtPrecio, txtStock;
    private JButton btnGuardar, btnCancelar;
    private ProductoDAO productoDAO;
    private Producto producto; // Para saber si estamos editando o creando
    private boolean guardado = false;

    // Constructor para AGREGAR un nuevo producto
    public FormularioProductos(Frame owner, ProductoDAO dao) {
        super(owner, "Agregar Producto", true);
        this.productoDAO = dao;
        this.producto = new Producto(); // Producto nuevo y vacío
        inicializarComponentes();
    }

    // Constructor para EDITAR un producto existente
    public FormularioProductos(Frame owner, ProductoDAO dao, Producto productoAEditar) {
        super(owner, "Editar Producto", true);
        this.productoDAO = dao;
        this.producto = productoAEditar; // Producto existente
        inicializarComponentes();
        cargarDatosProducto();
    }

    private void inicializarComponentes() {
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelCampos.add(txtDescripcion);

        panelCampos.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelCampos.add(txtPrecio);

        panelCampos.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelCampos.add(txtStock);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        // --- Action Listeners ---
        btnGuardar.addActionListener(e -> guardarProducto());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarDatosProducto() {
        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtDescripcion.setText(producto.getDescripcion());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtStock.setText(String.valueOf(producto.getStock()));
        }
    }

    private void guardarProducto() {
        try {
            // Recoger datos del formulario
            producto.setNombre(txtNombre.getText());
            producto.setDescripcion(txtDescripcion.getText());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setStock(Integer.parseInt(txtStock.getText()));

            if (producto.getId() == 0) { // Si el ID es 0, es un producto nuevo
                productoDAO.agregarProducto(producto);
            } else {
                productoDAO.actualizarProducto(producto);
            }
            guardado = true;
            dispose(); // Cierra la ventana del formulario

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y Stock deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isGuardado() {
        return guardado;
    }
}