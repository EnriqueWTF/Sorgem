
package dao;

import db.Conexion;
import modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VentaDAO {
    public boolean registrarVenta(Venta venta) {
        String sql = "INSERT INTO ventas (anio, mes, precio_unitario, promocion, cantidad_vendida) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Asignamos los valores a la consulta SQL
            pstmt.setInt(1, venta.getAnio());
            pstmt.setInt(2, venta.getMes());
            pstmt.setDouble(3, venta.getPrecioUnitario());
            pstmt.setInt(4, venta.getPromocion());
            pstmt.setInt(5, venta.getCantidadVendida());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se guardó correctamente

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
