$(document).ready(function () {
    // Constantes
    const LOGIN_API_URL = '/Almacen/login'; 
    const DASHBOARD_URL = '../VistaUsuario/Inicio.jsp';

    // Inicialización
    initLogin();

    function initLogin() {
        setupLoginForm();
    }

    function setupLoginForm() {
        $('#loginForm').submit(function (e) {
            e.preventDefault();
            handleLoginSubmit();
        });
    }

    function handleLoginSubmit() {
        resetErrorMessages();
        showLoading(true);

        if (!validateForm()) {
            showLoading(false);
            return;
        }

        authenticateUser();
    }

    function resetErrorMessages() {
        $('.error-message').text('');
        $('#serverResponse').text('').removeClass('success error');
    }

    function validateForm() {
        let isValid = true;
        const username = $('#Usuario').val().trim();
        const password = $('#Contraseña').val().trim();

        if (username === '') {
            $('#usernameError').text('El usuario es requerido');
            isValid = false;
        }

        if (password === '') {
            $('#passwordError').text('La contraseña es requerida');
            isValid = false;
        }

        return isValid;
    }

    function authenticateUser() {
        showLoading(true);
        const formData = {
            username: $('#Usuario').val().trim(),
            password: $('#Contraseña').val().trim()
        };

        $.ajax({
            type: 'POST',
            url: LOGIN_API_URL,
            data: JSON.stringify(formData),
            contentType: 'application/json',
            dataType: 'json',
            success: function (response) {
                handleLoginResponse(response);
            },
            error: function (xhr, status, error) {
                // Extraer el mensaje de error del response si existe
                let errorMessage = "Error en la conexión";
                try {
                    if (xhr.responseJSON && xhr.responseJSON.message) {
                        errorMessage = xhr.responseJSON.message;
                    } else if (xhr.responseText) {
                        const response = JSON.parse(xhr.responseText);
                        errorMessage = response.message || errorMessage;
                    }
                } catch (e) {
                    console.error("Error parsing error response:", e);
                }

                handleLoginError(errorMessage, xhr.status);
            }
        });
    }

    function handleLoginResponse(response) {
        showLoading(false);

        if (response.success) {
            window.location.href = DASHBOARD_URL;
        } else {
            showLoginError(response.message || 'Credenciales incorrectas');
        }
    }

    function handleLoginError(error) {
        showLoading(false);
        showLoginError('Error en la conexión: ' + error);

    }

    function showLoading(show) {
        if (show) {
            $('#loader').fadeIn();
        } else {
            $('#loader').fadeOut();
        }
    }

    function showLoginError(message) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: message,
            confirmButtonText: 'Entendido'
        });
    }
});