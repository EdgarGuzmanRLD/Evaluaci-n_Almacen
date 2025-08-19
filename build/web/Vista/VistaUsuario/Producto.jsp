<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession sessionOk = request.getSession(false);
    String usuario = "" + sessionOk.getAttribute("Nombre_Usuario");
    String PermisoBitacora = "" + (sessionOk.getAttribute("Ver módulo del histórico") == null ? 0 : sessionOk.getAttribute("Ver módulo del histórico"));
    String permisoAgregar = "" + (sessionOk.getAttribute("Agregar nuevos productos") == null ? 0 : sessionOk.getAttribute("Agregar nuevos productos"));
    String permisoAumentr = "" + (sessionOk.getAttribute("Aumentar inventario") == null ? 0 : sessionOk.getAttribute("Aumentar inventario"));
    String permisoBA = "" + (sessionOk.getAttribute("Dar de baja/reactivar un producto") == null ? 0 : sessionOk.getAttribute("Dar de baja/reactivar un producto"));
    String permisoSalida = "" + (sessionOk.getAttribute("Ver módulo para Salida de productos") == null ? 0 : sessionOk.getAttribute("Ver módulo para Salida de productos"));
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="../Imagenes/almacen.png"   type="image/png" />
        <meta charset="UTF-8">
        <title>Gestión de productos</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> 
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link href="../css/Inicio.css" rel="stylesheet" type="text/css"/>
        <link href="../css/Producto.css" rel="stylesheet" type="text/css"/>
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
                        <li><a href="Producto.jsp" class="active">Productos</a></li>
                            <%if (PermisoBitacora.equalsIgnoreCase("7")) {%>
                        <li><a href="Bitacora.jsp">Bitácora de movimientos</a></li>
                            <%}%>
                        <li><a href="../Login/Login.jsp">Salir</a></li>  
                    </ul>
                </nav>
            </div>

            <div class="main-content">

                <header>
                    <div>
                        <h1>Inventario de Productos</h1>
                    </div>
                    <div class="user-info">
                        <label id="nombre"><%=usuario%></label>
                    </div>
                </header>

                <!-- Pestañas -->
                <div class="tabs">
                    <%
                        boolean mostrarRegistro = permisoAgregar.equalsIgnoreCase("2") || permisoAumentr.equalsIgnoreCase("3");
                        boolean mostrarSalidas = permisoSalida.equalsIgnoreCase("5");

                        if (mostrarRegistro) {
                    %>
                    <button class="tab-button active" data-tab="registro-tab">Registro de Productos</button>
                    <button class="tab-button" data-tab="inventario-tab">Inventario</button>
                    <% }

                        if (mostrarSalidas) {
                    %>
                    <button class="tab-button <%= !mostrarRegistro ? "active" : ""%>" data-tab="salidas-tab">Salidas de Productos</button>
                    <% }
                    %>

                </div>

                <!-- Contenido de pestaña de Registro -->
                <div id="registro-tab" class="tab-content active">
                    <h2>Registrar Nuevo Producto</h2>
                    <form id="producto-form" class="product-form" method="POST">
                        <div class="form-group">
                            <label for="nombre-producto">Nombre del Producto:</label>
                            <input type="text" id="nombre-producto" name="nombre-producto" required>
                        </div>

                        <div class="form-group">
                            <label for="unidad-medida">Unidad de Medida:</label>
                            <select id="unidad-medida" name="unidad-medida" required>
                                <option value="0">Seleccione una unidad</option>
                            </select>
                        </div>

                        <button type="submit" class="submit-button">Guardar Producto</button>
                    </form>

                    <table class="product-tableP">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Unidad</th>
                                <th>Cantidad</th>   
                                <th>Estatus</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Datos se cargarán dinámicamente -->
                        </tbody>
                    </table>
                </div>

                <!-- Contenido de pestaña de Inventario -->
                <div id="inventario-tab" class="tab-content">
                    <h2>Inventario de Productos</h2>
                    <table class="product-tableP">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Unidad</th>
                                <th>Cantidad</th>   
                                <th>Estatus</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Datos se cargarán dinámicamente -->
                        </tbody>
                    </table>
                </div>

                <!-- Contenido de pestaña de Salidas -->
                <div id="salidas-tab" class="tab-content">
                    <h2>Registro de Salidas</h2>
                    <table class="product-tableP">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Unidad</th>
                                <th>Cantidad Disponible</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Datos se cargarán dinámicamente -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Modal para acciones -->
        <div id="accion-modal" class="modal">
            <div class="modal-content">
                <span class="close-modal">&times;</span>
                <h2 id="modal-title">Acción con Producto</h2>
                <form id="accion-form">
                    <input type="hidden" id="producto-id">
                    <input type="hidden" id="cantidadAnterior">
                    <input type="hidden" id="unidad-id">
                    <div class="form-group">
                        <label id="producto-nombre-label">Producto:</label>
                        <span id="producto-nombre"></span>
                    </div>

                    <div class="form-group">
                        <label for="cantidad">Cantidad:</label>
                        <input type="number" id="cantidad" name="cantidad" min="1" required>
                    </div>

                    <button type="submit" id="modal-submit" class="submit-button">Confirmar</button>
                </form>
            </div>
        </div>
        <!-- Loader -->
        <div class="custom-loader" id="loader"></div>
        <script src="../js/Producto.js" type="text/javascript"></script>
    </body>
</html>