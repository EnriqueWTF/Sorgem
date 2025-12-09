package dao;

import db.Conexion;
import modelo.DetalleVenta;
import modelo.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // Método para obtener historial (Ya lo tenías, lo dejo igual)
    public List<Venta> obtenerTodas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.id, v.cliente_id, c.nombre AS cliente_nombre, v.fecha, v.total " +
                "FROM ventas v INNER JOIN clientes c ON v.cliente_id = c.id ORDER BY v.fecha DESC";
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Venta v = new Venta();
                v.setId(rs.getInt("id"));
                v.setClienteId(rs.getInt("cliente_id"));
                v.setNombreCliente(rs.getString("cliente_nombre"));
                v.setFecha(rs.getString("fecha"));
                v.setTotal(rs.getDouble("total"));
                ventas.add(v);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return ventas;
    }

    // --- NUEVO: REGISTRAR VENTA COMPLETA ---
    public boolean registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        Connection conn = Conexion.getConexion();
        PreparedStatement psVenta = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;

        String sqlVenta = "INSERT INTO ventas (cliente_id, total) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (venta_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id = ?";

        try {
            // 1. Desactivar auto-guardado para manejar transacción manual
            conn.setAutoCommit(false);

            // 2. Insertar Venta (Cabecera)
            psVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setInt(1, venta.getClienteId());
            psVenta.setDouble(2, venta.getTotal());
            psVenta.executeUpdate();

            // Obtener el ID generado de la venta
            ResultSet rsKeys = psVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rsKeys.next()) {
                idVenta = rsKeys.getInt(1);
            }

            // 3. Insertar Detalles y Restar Stock
            psDetalle = conn.prepareStatement(sqlDetalle);
            psStock = conn.prepareStatement(sqlStock);

            for (DetalleVenta d : detalles) {
                // Guardar detalle
                psDetalle.setInt(1, idVenta);
                psDetalle.setInt(2, d.getProductoId());
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.setDouble(4, d.getPrecioUnitario());
                psDetalle.executeUpdate();

                // Restar stock
                psStock.setInt(1, d.getCantidad());
                psStock.setInt(2, d.getProductoId());
                psStock.executeUpdate();
            }

            // 4. Confirmar cambios si todo salió bien
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // Si falla algo, deshacer todo
            } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
                if (psVenta != null) psVenta.close();
                if (psDetalle != null) psDetalle.close();
                if (psStock != null) psStock.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
