/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Servicios.AuthService;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            // Leer credenciales del cuerpo JSON
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }

            Map<String, String> credentials = gson.fromJson(sb.toString(), Map.class);
            String username = credentials.get("username");
            String password = credentials.get("password");

            // Autenticar
            Map<String, Object> authResult = authService.authenticate(username, password);

            if (Boolean.TRUE.equals(authResult.get("authenticated"))) {
                // Crear sesión con todos los datos
                HttpSession session = request.getSession();
                session.setAttribute("Id_Usuario", authResult.get("Id_Usuario"));
                session.setAttribute("Nombre_Usuario", authResult.get("Nombre_Usuario"));
                session.setAttribute("Correo", authResult.get("Correo"));

                // Guardar cada permiso como atributo individual
                Map<String, Integer> permisos = (Map<String, Integer>) authResult.get("permisos");
                for (Map.Entry<String, Integer> entry : permisos.entrySet()) {
                    session.setAttribute(entry.getKey(), entry.getValue());
                }

                // Respuesta exitosa
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("success", true);
                responseData.put("message", "Autenticación exitosa");
                responseData.put("user", authResult.get("Nombre_Usuario"));
                responseData.put("permisos", permisos.keySet());

                response.getWriter().write(gson.toJson(responseData));
            } else {
                // Respuesta de error
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", authResult.getOrDefault("error", "Credenciales incorrectas"));
                response.getWriter().write(gson.toJson(errorResponse));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error en el servidor");
            response.getWriter().write(gson.toJson(error));
            e.printStackTrace();
        }
    }
}
