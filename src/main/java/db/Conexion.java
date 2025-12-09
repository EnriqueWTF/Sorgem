package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/sorgem?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "140899jimM";

    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: Falta el Driver de MySQL (JAR).\n" + e.getMessage());
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de Conexión a Base de Datos:\n" + e.getMessage());
            return null;
        }
    }
}