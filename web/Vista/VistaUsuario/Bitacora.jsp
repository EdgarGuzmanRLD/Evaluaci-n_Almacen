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
        <title>Bitácora de Movimientos</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link href="../css/Inicio.css" rel="stylesheet" type="text/css"/>
        <link href="../css/Bitacora.css" rel="stylesheet" type="text/css"/>
        <link href="../css/Loader.css" rel="stylesheet" type="text/css"/>

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
                        <li><a href="Bitacora.jsp" class="active">Bitácora de movimientos</a></li>
                            <%}%>
                        <li><a href="../Login/Login.jsp">Salir</a></li>
                    </ul>
                </nav>
            </div>

            <div class="main-content">
                <header>
                    <div>
                        <h1>Bitacora de Movimientos</h1>
                    </div>
                    <div class="user-info">
                        <label id="nombre"><%=usuario%></label>
                    </div>
                </header>

                <div class="bitacora-content">
                    <h2>Bitácora de Movimientos</h2>

                    <div class="filter-buttons">
                        <button class="filter-btn active" data-filter="all">Todos</button>
                        <button class="filter-btn" data-filter="ingreso">Ingresos</button>
                        <button class="filter-btn" data-filter="salida">Salidas</button>
                    </div>

                    <table class="bitacora-table">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Usuario</th>
                                <th>Tipo de Movimiento</th>
                                <th>Cantidad</th>
                                <th>Fecha</th>
                                <th>Hora</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Datos se cargarán dinámicamente -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="custom-loader" id="loader"></div>
        <script src="../js/Bitacora.js" type="text/javascript"></script>
    </body>
</html>