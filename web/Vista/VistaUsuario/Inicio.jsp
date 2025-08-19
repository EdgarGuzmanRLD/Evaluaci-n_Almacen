<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession sessionOk = request.getSession(false);
    String usuario = "" + sessionOk.getAttribute("Nombre_Usuario");
    String PermisoBitacora = "" + (sessionOk.getAttribute("Ver módulo del histórico") == null ? 0 : sessionOk.getAttribute("Ver módulo del histórico"));

%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="../Imagenes/almacen.png"   type="image/png" />
        <meta charset="UTF-8">
        <title>Sistema de Inventario</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link href="../css/Inicio.css" rel="stylesheet" type="text/css"/>
        <script src="../js/Inicio.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="container">
            <!-- Menú lateral -->
            <div class="sidebar">
                <div class="logo">
                    <a href="Inicio.jsp" style="text-decoration: none; color: inherit;">
                        <h1>Inventario</h1>
                    </a>
                </div>
                <nav>
                    <ul>
                        <li><a href="Producto.jsp">Productos</a></li>
                            <%if (PermisoBitacora.equalsIgnoreCase("7")) {%>
                        <li><a href="Bitacora.jsp">Bitácora de movimientos</a></li>
                            <%}%>
                        <li><a href="../Login/Login.jsp">Salir</a></li>
                    </ul>
                </nav>
            </div>

            <!-- Contenido principal -->
            <div class="main-content">
                <header>
                    <div>
                        <h1>Panel De Inicio</h1>
                    </div>
                    <div class="user-info">
                        <label id="nombre">
                            <%=usuario%>
                        </label>
                    </div>
                </header>

                <div class="content">
                    <h2>Resumen de Productos</h2>
                    <table class="product-table">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Cantidad</th>
                                <th>Unidad de medida</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="custom-loader" id="loader"></div>       
    </body>
</html>