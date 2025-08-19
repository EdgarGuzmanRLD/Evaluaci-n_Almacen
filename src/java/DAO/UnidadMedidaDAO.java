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


        try (Connection conn = conexion.Conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))  {


            while (rs.next()) {
                unidades.add(new Unidad_Medida(
                        rs.getInt("Id_Unidad"),
                        rs.getString("Unidad")
                ));
            }
        } 

        return unidades;
    }

}
