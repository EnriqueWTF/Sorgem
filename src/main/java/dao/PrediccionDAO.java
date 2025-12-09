package dao;

import db.Conexion;
import modelo.PrediccionIA;
import java.sql.*;

public class PrediccionDAO {

    // Obtiene solo la última predicción generada
    public PrediccionIA obtenerUltimaPrediccion() {
        PrediccionIA prediccion = null;
        String sql = "SELECT * FROM predicciones_ia ORDER BY id DESC LIMIT 1";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                prediccion = new PrediccionIA();
                prediccion.setMejorMes(rs.getInt("mejor_mes"));
                prediccion.setCantidadEstimada(rs.getInt("cantidad_estimada"));
                prediccion.setMensaje(rs.getString("mensaje"));
                prediccion.setFechaCalculo(rs.getString("fecha_calculo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prediccion;
    }
}
