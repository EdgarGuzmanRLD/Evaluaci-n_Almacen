package Control;

import Servicios.ProductoService;
import Modelo.*;
import Servicios.BitacoraService;
import Servicios.UnidadMedidaService;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ProductoController", urlPatterns = {"/productos"})
public class ProductoController extends HttpServlet {
    private final ProductoService productoService;
    private final UnidadMedidaService unidadMedidaService;
    private final BitacoraService bitacoraService;
    private final Gson gson = new Gson();

    public ProductoController() {
        this.productoService = new ProductoService();
        this.unidadMedidaService = new UnidadMedidaService();
        this.bitacoraService = new BitacoraService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String action = request.getParameter("action");
            String jsonResponse;
            
            switch (action) {
                case "consultarTabla":
                    int activos = Integer.parseInt(request.getParameter("activos"));
                    jsonResponse = gson.toJson(productoService.consultarProductos(activos));
                    break;
                    
                case "consultarUnidades":
                    jsonResponse = gson.toJson(unidadMedidaService.consultarUnidades());
                    break;
                    
                case "registrarProducto":
                    jsonResponse = productoService.registrarProducto(
                            request.getParameter("producto"),
                            Integer.parseInt(request.getParameter("unidad"))
                    );
                    break;
                    
                case "cambiarEstatus":
                    jsonResponse = productoService.cambiarEstatus(
                            Integer.parseInt(request.getParameter("Id_Producto")),
                            Integer.parseInt(request.getParameter("estatusCambiado"))
                    );
                    break;
                    
                case "añadir_disminuir_Cantidad":
                    HttpSession session = request.getSession();
                    jsonResponse = productoService.ajustarCantidad(
                            Integer.parseInt(session.getAttribute("Id_Usuario").toString()),
                            Integer.parseInt(request.getParameter("Id_Producto")),
                            Integer.parseInt(request.getParameter("cantidad")),
                            Integer.parseInt(request.getParameter("accionArealizar"))
                    );
                    break;
                    
                case "consultarBitacora":
                    jsonResponse = gson.toJson(bitacoraService.consultarBitacora(
                            request.getParameter("filtro"))
                    );
                    break;
                    
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    jsonResponse = "{\"error\":\"Acción no válida\"}";
            }
            
            response.getWriter().write(jsonResponse);
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Parámetros inválidos\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}