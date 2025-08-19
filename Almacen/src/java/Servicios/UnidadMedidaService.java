package Servicios;

import Modelo.Unidad_Medida;
import DAO.UnidadMedidaDAO;
import java.sql.SQLException;
import java.util.List;

public class UnidadMedidaService {
    private final UnidadMedidaDAO unidadMedidaDAO;
    
    public UnidadMedidaService() {
        this.unidadMedidaDAO = new UnidadMedidaDAO();
    }
    
    public List<Unidad_Medida> consultarUnidades() throws SQLException {
        return unidadMedidaDAO.obtenerUnidades();
    }
}