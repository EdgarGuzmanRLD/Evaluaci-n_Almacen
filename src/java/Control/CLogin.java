package Control;

import Modelo.Permisos;
import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edgar
 */
@WebServlet(name = "CLogin", urlPatterns = {"/CLogin"})
public class CLogin extends HttpServlet {

    HttpSession session;
    HttpServletRequest request;
    List<Permisos> lstPermiso;
    Usuario user;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        session = request.getSession();
        Conexion conexion = new Conexion();
        Connection conn = conexion.Conectar();
        //Obtenemos los datos del formulario
        String Usuario = request.getParameter("Usuario");
        String Contraseña = request.getParameter("Contraseña");
        
        if (Usuario == null || Usuario.trim().isEmpty() || Contraseña == null || Contraseña.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("Usuario y contraseña son requeridos");
            return;
        }

        //Acto seguido realizaremos una consulta para verificar que estos datos se encuentren en la BD
        String queryConsulta = "SELECT U.Id_Usuario,U.Nombre_Usuario ,U.Correo, PRI.Id_Permiso, P.Nombre_Permiso\n"
                + "FROM Usuarios as U\n"
                + "INNER JOIN Permisos_Roles_Intermedia AS PRI ON PRI.Id_Rol = U.Id_Rol\n"
                + "INNER JOIN Permisos AS P ON P.Id_Permiso = PRI.Id_Permiso\n"
                + "WHERE Nombre_Usuario='" + Usuario + "' and Contraseña='" + Contraseña + "' and EstatuS=1";

        try {
            user = new Usuario();
            user.setId_Usuario(0);
            lstPermiso= new ArrayList<>();
        
        Statement smtConsulta = conn.createStatement();
        ResultSet rs= smtConsulta.executeQuery(queryConsulta);
        
        
        while (rs.next()){
            Permisos permisoAux = new Permisos();
            permisoAux.setId_Permiso(rs.getInt("Id_Permiso"));
            permisoAux.setNombre_Permiso(rs.getString("Nombre_Permiso"));
            user.setId_Usuario(rs.getInt("Id_Usuario"));
            user.setNombre_Usuario(rs.getString("Nombre_Usuario"));
            user.setCorreo(rs.getString("Correo"));
            lstPermiso.add(permisoAux);
        }
        
        if(user.getId_Usuario()!=0){
            session.setAttribute("Id_Usuario", user.getId_Usuario());
            session.setAttribute("Nombre_Usuario", user.getNombre_Usuario());
            session.setAttribute("Correo", user.getCorreo());
            lstPermiso.forEach((permiso) -> {
                session.setAttribute(permiso.getNombre_Permiso(), permiso.getId_Permiso());
            });
            conn.close();
            session.setMaxInactiveInterval(30 * 60);
            out.print("Inicio Exitoso");
        } else {
                conn.close();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("Credenciales inválidas o usuario inactivo");
            }
        } catch (Exception e) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

    
  }
}
