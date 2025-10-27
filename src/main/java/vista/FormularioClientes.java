package vista; // Asegúrate de que el paquete sea 'vista'

import dao.ClienteDAO;
import modelo.Cliente;

import javax.swing.*;
import java.awt.*;

// Importa las clases DAO y Modelo que necesitas
import dao.ClienteDAO;
import modelo.Cliente;

public class FormularioClientes extends JDialog {

    private JTextField txtNombre, txtTelefono, txtEmail;
    private JButton btnGuardar, btnCancelar;
    private ClienteDAO clienteDAO;
    private Cliente cliente; // Para saber si estamos editando o creando
    private boolean guardado = false;

    // Constructor para AGREGAR
    public FormularioClientes(Frame owner, ClienteDAO dao) {
        super(owner, "Agregar Cliente", true);
        this.clienteDAO = dao;
        this.cliente = new Cliente(); // Cliente nuevo y vacío
        inicializarComponentes();
    }

    // Constructor para EDITAR
    public FormularioClientes(Frame owner, ClienteDAO dao, Cliente clienteAEditar) {
        super(owner, "Editar Cliente", true);
        this.clienteDAO = dao;
        this.cliente = clienteAEditar; // Cliente existente
        inicializarComponentes();
        cargarDatosCliente();
    }

    private void inicializarComponentes() {
        setSize(400, 250); // Un poco más bajo que el de productos
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelCampos.add(txtTelefono);

        panelCampos.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelCampos.add(txtEmail);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        // --- Action Listeners ---
        btnGuardar.addActionListener(e -> guardarCliente());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarDatosCliente() {
        if (cliente != null) {
            txtNombre.setText(cliente.getNombre());
            txtTelefono.setText(cliente.getTelefono());
            txtEmail.setText(cliente.getEmail());
        }
    }

    private void guardarCliente() {
        // Validación simple
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Recoger datos del formulario
        cliente.setNombre(txtNombre.getText());
        cliente.setTelefono(txtTelefono.getText());
        cliente.setEmail(txtEmail.getText());

        if (cliente.getId() == 0) { // Si el ID es 0, es un cliente nuevo
            clienteDAO.agregarCliente(cliente);
        } else { // Si tiene ID, es una actualización
            clienteDAO.actualizarCliente(cliente);
        }

        guardado = true;
        dispose(); // Cierra la ventana del formulario
    }

    public boolean isGuardado() {
        return guardado;
    }
}