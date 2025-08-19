package DAO;

import Control.Conexion;
import Modelo.Unidad_Medida;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadMedidaDAO {

    private final Conexion conexion;

    public UnidadMedidaDAO() {
        this.conexion = new Conexion();
    }

    public List<Unidad_Medida> obtenerUnidades() throws SQLException {
        List<Unidad_Medida> unidades = new ArrayList<>();
        String sql = "SELECT Id_Unidad, Unidad FROM Unidad_Medida";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = conexion.Conectar();
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Conexión a la base de datos no disponible");
            }

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                unidades.add(new Unidad_Medida(
                        rs.getInt("Id_Unidad"),
                        rs.getString("Unidad")
                ));
            }
        } catch (SQLException e) {
            // Loggear el error
            System.err.println("Error al obtener unidades de medida: " + e.getMessage());
            throw e; // Relanzar la excepción
        } finally {
            closeResources(rs, stmt, conn);
        }

        return unidades;
    }

    private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar ResultSet: " + e.getMessage());
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar Statement: " + e.getMessage());
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar Connection: " + e.getMessage());
        }
    }
}
