package DAO;

import Control.Conexion;
import Modelo.Bitacora_Movimientos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BitacoraDAO {
    private final Conexion conexion;
    
    public BitacoraDAO() {
        this.conexion = new Conexion();
    }
    
    public List<Bitacora_Movimientos> obtenerMovimientos(String filtro) throws SQLException {
        List<Bitacora_Movimientos> movimientos = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT P.Nombre_Producto, U.Nombre_Usuario, " +
            "CASE WHEN BMP.Tipo_Movimiento = 1 THEN 'INGRESO' ELSE 'SALIDA' END AS Tipo_Movimiento, " +
            "BMP.Cantidad_Movimiento, " +
            "CONVERT(VARCHAR(10), Fecha_Movimiento, 120) AS Fecha, " +
            "CONVERT(VARCHAR(8), Fecha_Movimiento, 108) AS Hora " +
            "FROM Bitacora_Movimientos_Productos AS BMP " +
            "INNER JOIN Producto AS P ON P.Id_Producto = BMP.Id_Producto " +
            "INNER JOIN Usuarios AS U ON U.Id_Usuario = BMP.Id_Usuario"
        );
        
        if (filtro != null) {
            if (filtro.equalsIgnoreCase("ingreso")) {
                sql.append(" WHERE Tipo_Movimiento = 1");
            } else if (filtro.equalsIgnoreCase("salida")) {
                sql.append(" WHERE Tipo_Movimiento = 0");
            }
        }
        
        try (Connection conn = conexion.Conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                movimientos.add(new Bitacora_Movimientos(
                    rs.getString("Nombre_Producto"),
                    rs.getString("Nombre_Usuario"),
                    rs.getString("Tipo_Movimiento"),
                    rs.getFloat("Cantidad_Movimiento"),
                    rs.getString("Fecha"),
                    rs.getString("Hora")
                ));
            }
        }
        return movimientos;
    }
}