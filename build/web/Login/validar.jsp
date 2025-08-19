<%@ page import="java.sql.*" %>
<%
    String user = request.getParameter("usuario");
    String pass = request.getParameter("clave");

    String url = "jdbc:sqlserver://localhost:1433;databaseName=Almacen;encrypt=true;trustServerCertificate=true;";
    String usuarioBD = "sa";   // tu usuario SQL Server
    String claveBD = "12345";  // tu contraseña SQL Server

    boolean acceso = false;

    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url, usuarioBD, claveBD);

        String sql = "SELECT * FROM Usuarios WHERE Usuario = ? AND Clave = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user);
        ps.setString(2, pass);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            acceso = true;
        }

        rs.close();
        ps.close();
        conn.close();
    } catch (Exception e) {
        out.println("? Error en la conexión: " + e.getMessage());
    }

    if (acceso) {
        // Guardar sesión
        session.setAttribute("usuarioLogeado", user);
        response.sendRedirect("bienvenido.jsp");
    } else {
        out.println("<p style='color:red;'>Usuario o contraseña incorrectos</p>");
        out.println("<a href='login.jsp'>Volver</a>");
    }
%>
