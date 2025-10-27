package vista;

import dao.ClienteDAO;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaClientes extends JDialog {

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClienteDAO clienteDAO;

    public VentanaClientes(JFrame owner) {
        super(owner, "Gestión de Clientes", true);
        setSize(800, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        clienteDAO = new ClienteDAO();

        // Modelo de la tabla
        String[] columnas = {"ID", "Nombre", "Teléfono", "Email"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaClientes = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
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

        // Llenar la tabla
        cargarClientes();

        // --- Action Listeners ---

        // 1. Botón AGREGAR
        btnAgregar.addActionListener(e -> {
            FormularioClientes formulario = new FormularioClientes((JFrame) getOwner(), clienteDAO);
            formulario.setVisible(true);

            if (formulario.isGuardado()) {
                cargarClientes();
            }
        });

        // 2. Botón EDITAR
        btnEditar.addActionListener(e -> {
            int filaSeleccionada = tablaClientes.getSelectedRow();
            if (filaSeleccionada != -1) {
                // Obtenemos los datos de la fila
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
                String telefono = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
                String email = (String) modeloTabla.getValueAt(filaSeleccionada, 3);

                Cliente clienteSeleccionado = new Cliente();
                clienteSeleccionado.setId(id);
                clienteSeleccionado.setNombre(nombre);
                clienteSeleccionado.setTelefono(telefono);
                clienteSeleccionado.setEmail(email);

                // Abrimos el formulario pasándole el cliente
                FormularioClientes formulario = new FormularioClientes((JFrame) getOwner(), clienteDAO, clienteSeleccionado);
                formulario.setVisible(true);

                if (formulario.isGuardado()) {
                    cargarClientes();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un cliente para editar.");
            }
        });

        // 3. Botón ELIMINAR
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaClientes.getSelectedRow();
            if (filaSeleccionada != -1) {
                int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que quieres eliminar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    clienteDAO.eliminarCliente(idCliente);
                    cargarClientes();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un cliente para eliminar.");
            }
        });
    }

    /**
     * Limpia y recarga la tabla con datos de la BD.
     */
    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        for (Cliente c : clientes) {
            Object[] fila = {
                    c.getId(),
                    c.getNombre(),
                    c.getTelefono(),
                    c.getEmail()
            };
            modeloTabla.addRow(fila);
        }
    }
}