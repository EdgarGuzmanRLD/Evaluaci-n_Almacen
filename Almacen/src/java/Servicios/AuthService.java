/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Control.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthService {

    private final Conexion conexion;

    public AuthService() {
        this.conexion = new Conexion();
    }

    public Map<String, Object> authenticate(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<String, Object> result = new HashMap<>();

        try {
            conn = conexion.Conectar();
            String sql = "SELECT U.Id_Usuario, U.Nombre_Usuario, U.Correo, \n"
                    + "PRI.Id_Permiso, P.Nombre_Permiso \n"
                    + "FROM Usuarios as U \n"
                    + "INNER JOIN Permisos_Roles_Intermedia AS PRI ON PRI.Id_Rol = U.Id_Rol \n"
                    + "INNER JOIN Permisos AS P ON P.Id_Permiso = PRI.Id_Permiso \n"
                    + "WHERE U.Nombre_Usuario = ? AND U.Contraseña = ? AND U.Estatus = 1";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            boolean hasUser = false;
            Map<String, Integer> permisosMap = new HashMap<>();

            while (rs.next()) {
                if (!hasUser) {
                    // Datos básicos del usuario (solo una vez)
                    result.put("Id_Usuario", rs.getInt("Id_Usuario"));
                    result.put("Nombre_Usuario", rs.getString("Nombre_Usuario"));
                    result.put("Correo", rs.getString("Correo"));
                    hasUser = true;
                }

                // Agregar cada permiso al mapa
                permisosMap.put(rs.getString("Nombre_Permiso"), rs.getInt("Id_Permiso"));
            }

            if (hasUser) {
                result.put("authenticated", true);
                result.put("permisos", permisosMap);
            } else {
                result.put("authenticated", false);
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            result.put("error", "Error en la base de datos");
        } finally {
            // Cerrar recursos (rs, pstmt, conn)
        }

        return result;
    }
}
