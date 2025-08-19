<%-- 
    Document   : Login
    Created on : 16/08/2025, 09:55:23 PM
    Author     : edgar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%   HttpSession sesion = request.getSession(false); 
    if (sesion != null) {
        sesion.invalidate(); 
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="../Imagenes/almacen.png"   type="image/png" />
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <link href="../css/Login.css" rel="stylesheet" type="text/css"/>
        <link href="../css/Loader.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="login-container">
            <div class="login-header">
                <h2><i class="fas fa-lock"></i> Iniciar Sesión</h2>
            </div>

            <!-- Mensajes de estado -->
            <div class="error-message" id="errorMessage">
                <i class="fas fa-exclamation-circle"></i> Usuario o contraseña incorrectos
            </div>

            <div class="success-message" id="successMessage">
                <i class="fas fa-check-circle"></i> ¡Sesión iniciada correctamente!
            </div>

            <form class="login-form" id="loginForm" method="POST">
                <div class="input-group">
                    <label for="username"><i class="fas fa-user"></i> Usuario</label>
                    <input type="text" id="Usuario" name="Usuario" placeholder="Ingresa tu usuario" required>
                </div>

                <div class="input-group">
                    <label for="password"><i class="fas fa-key"></i> Contraseña</label>
                    <input type="password" id="Contraseña" name="Contraseña" placeholder="Ingresa tu contraseña" required>
                </div>

                <button type="submit" class="btn-login">
                    <i class="fas fa-sign-in-alt"></i> Ingresar
                </button>
            </form>

        </div>

        <div id="loader" class="loader-container" style="display:none;">
            <div>
                <div class="loader"></div>
                <div class="loader-text">Cargando...</div>
            </div>
        </div>
        <script src="../js/Login.js" type="text/javascript"></script>

    </body>
</html>