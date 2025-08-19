package Servicios;

import Modelo.Producto;
import DAO.ProductoDAO;
import java.sql.SQLException;
import java.util.List;

public class ProductoService {
    private final ProductoDAO productoDAO;
    
    public ProductoService() {
        this.productoDAO = new ProductoDAO();
    }
    
    public List<Producto> consultarProductos(int activos) throws SQLException {
        return productoDAO.obtenerProductos(activos == 1);
    }
    
    public String registrarProducto(String nombre, int idUnidad) throws SQLException {
        return productoDAO.insertarProducto(nombre, idUnidad) ? "success" : "error";
    }
    
    public String cambiarEstatus(int idProducto, int nuevoEstatus) throws SQLException {
        return productoDAO.actualizarEstatus(idProducto, nuevoEstatus) ? "success" : "error";
    }
    
    public String ajustarCantidad(int idUsuario, int idProducto, float cantidad, int tipoMovimiento) throws SQLException {
        return productoDAO.ajustarCantidadProducto(idUsuario, idProducto, cantidad, tipoMovimiento) ? "success" : "error";
    }
}