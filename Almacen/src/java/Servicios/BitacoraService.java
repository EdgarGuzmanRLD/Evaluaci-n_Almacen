package Servicios;

import Modelo.Bitacora_Movimientos;
import DAO.BitacoraDAO;
import java.sql.SQLException;
import java.util.List;

public class BitacoraService {
    private final BitacoraDAO bitacoraDAO;
    
    public BitacoraService() {
        this.bitacoraDAO = new BitacoraDAO();
    }
    
    public List<Bitacora_Movimientos> consultarBitacora(String filtro) throws SQLException {
        return bitacoraDAO.obtenerMovimientos(filtro);
    }
}