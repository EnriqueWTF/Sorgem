package vista;

import dao.ProductoDAO;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaProductos extends JDialog {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private ProductoDAO productoDAO;

    public VentanaProductos(JFrame owner) {
        super(owner, "Gestión de Productos", true);
        setSize(800, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        productoDAO = new ProductoDAO();

        // Modelo de la tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hace que la tabla no sea editable directamente
                return false;
            }
        };
        tablaProductos = new JTable(modeloTabla);

        // Panel para la tabla con scroll
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        // Llenar la tabla con datos iniciales de la BD
        cargarProductos();

        // --- Action Listeners para los botones ---

        // 1. Botón AGREGAR: Abre un formulario vacío
        btnAgregar.addActionListener(e -> {
            FormularioProductos formulario = new FormularioProductos((JFrame) getOwner(), productoDAO);
            formulario.setVisible(true);

            // Si el formulario se cerró guardando, actualizamos la tabla
            if (formulario.isGuardado()) {
                cargarProductos();
            }
        });

        // 2. Botón EDITAR: Abre un formulario con los datos del producto seleccionado
        btnEditar.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada != -1) {
                // Obtenemos los datos del producto de la fila seleccionada
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
                String desc = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
                double precio = (double) modeloTabla.getValueAt(filaSeleccionada, 3);
                int stock = (int) modeloTabla.getValueAt(filaSeleccionada, 4);

                // Creamos un objeto Producto con esos datos
                Producto productoSeleccionado = new Producto();
                productoSeleccionado.setId(id);
                productoSeleccionado.setNombre(nombre);
                productoSeleccionado.setDescripcion(desc);
                productoSeleccionado.setPrecio(precio);
                productoSeleccionado.setStock(stock);

                // Abrimos el formulario pasándole el producto a editar
                FormularioProductos formulario = new FormularioProductos((JFrame) getOwner(), productoDAO, productoSeleccionado);
                formulario.setVisible(true);

                // Si se guardaron cambios, actualizamos la tabla
                if (formulario.isGuardado()) {
                    cargarProductos();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para editar.");
            }
        });

        // 3. Botón ELIMINAR: Borra el producto seleccionado
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada != -1) {
                int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que quieres eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    productoDAO.eliminarProducto(idProducto);
                    cargarProductos(); // Recargar la tabla para mostrar el cambio
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.");
            }
        });
    }

    /**
     * Limpia la tabla y la vuelve a llenar con los datos actualizados
     * de la base de datos.
     */
    private void cargarProductos() {
        // Limpiar tabla antes de cargar
        modeloTabla.setRowCount(0);

        List<Producto> productos = productoDAO.obtenerTodos();
        for (Producto p : productos) {
            Object[] fila = {
                    p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getPrecio(),
                    p.getStock()
            };
            modeloTabla.addRow(fila);
        }
    }
}