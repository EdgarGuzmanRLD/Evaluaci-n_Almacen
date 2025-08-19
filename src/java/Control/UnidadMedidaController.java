/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Servicios.UnidadMedidaService;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author edgar
 */
@WebServlet(name = "UnidadMedidaController", urlPatterns = {"/unidadMedida"})
public class UnidadMedidaController extends HttpServlet {

    private final UnidadMedidaService unidadMedidaService;
    private final Gson gson = new Gson();

    public UnidadMedidaController() {
        this.unidadMedidaService = new UnidadMedidaService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String action = request.getParameter("action");
            String jsonResponse;

            switch (action) {
                case "consultarUnidades":
                    jsonResponse = gson.toJson(unidadMedidaService.consultarUnidades());
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
