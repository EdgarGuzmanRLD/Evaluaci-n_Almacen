package DAO;

import Control.Conexion;
import Modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private final Conexion conexion;
    
    public ProductoDAO() {
        this.conexion = new Conexion();
    }
    
    public List<Producto> obtenerProductos(boolean soloActivos) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT Id_Producto, Nombre_Producto, Cantidad, P.Id_Unidad, UM.Unidad, P.Estatus " +
                      "FROM Producto P INNER JOIN Unidad_Medida UM ON UM.Id_Unidad = P.Id_Unidad" +
                      (soloActivos ? " WHERE P.Estatus=1" : "");
        
        try (Connection conn = conexion.Conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("Id_Producto"),
                    rs.getString("Nombre_Producto"),
                    rs.getFloat("Cantidad"),
                    rs.getInt("Id_Unidad"),
                    rs.getString("Unidad"),
                    rs.getInt("Estatus")
                ));
            }
        }
        return productos;
    }
    
    public boolean insertarProducto(String nombre, int idUnidad) throws SQLException {
        String sql = "INSERT INTO Producto VALUES (?, 0, ?, 1)";
        
        try (Connection conn = conexion.Conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, idUnidad);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean actualizarEstatus(int idProducto, int nuevoEstatus) throws SQLException {
        String sql = "UPDATE Producto SET Estatus=? WHERE Id_Producto=?";
        
        try (Connection conn = conexion.Conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevoEstatus);
            pstmt.setInt(2, idProducto);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean ajustarCantidadProducto(int idUsuario, int idProducto, float cantidad, int tipoMovimiento) throws SQLException {
        String sql = "EXEC cambiar_Cantidad ?, ?, ?, ?";
        
        try (Connection conn = conexion.Conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idProducto);
            pstmt.setFloat(3, cantidad);
            pstmt.setInt(4, tipoMovimiento);
            pstmt.execute();
            return true;
        }
    }
}