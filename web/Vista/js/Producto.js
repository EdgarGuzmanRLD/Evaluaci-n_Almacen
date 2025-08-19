
const API_BASE_URL = '/Almacen/productos';
const API_BASE_URL_Unidades = '/Almacen/unidadMedida';

// Inicializar la primera pestaña como activa
const firstAvailableTab = $('.tab-button').first().data('tab') || 'salidas-tab';
$(document).ready(function () {

    // Cargar unidades de medida al inicio
    cargarUnidades();

    // Manejar clic en botones de pestañas
    $('.tab-button').on('click', function () {
        const tabId = $(this).data('tab');
        openTab(tabId);
    });

    // Manejar clic en botón cerrar modal
    $('.close-modal').on('click', function () {
        $('#accion-modal').hide();
    });

    // Manejar clic fuera del modal
    $(window).on('click', function (event) {
        if ($(event.target).is('#accion-modal')) {
            $('#accion-modal').hide();
        }
    });

    // Manejar envío del formulario de acción
    $('#accion-form').on('submit', function (e) {
        e.preventDefault();
        procesarAccionProducto();
    });
});

function openTab(tabId) {
    showLoading();
    $('.tab-content').removeClass('active');
    $('.tab-button').removeClass('active');

    // Mostrar el contenido seleccionado
    $('#' + tabId).addClass('active');
    $(`.tab-button[data-tab="${tabId}"]`).addClass('active');

    // Cargar productos según la pestaña
    if (tabId === 'inventario-tab') {
        cargarProductos(2, '#inventario-tab .product-tableP tbody');
    } else if (tabId === 'salidas-tab') {
        cargarProductos(3, '#salidas-tab .product-tableP tbody');
    } else if (tabId === 'registro-tab') {
        cargarProductos(1, '#registro-tab .product-tableP tbody');
    }
}

function cargarUnidades() {
    $.ajax({
        type: 'POST',
        url: API_BASE_URL_Unidades,
        data: {action: 'consultarUnidades'},
        dataType: 'json',
        success: function (response) {
            const $select = $('#unidad-medida');
            $select.empty().append('<option value="">Seleccione una unidad</option>');

            $.each(response, function (index, unidad) {
                $select.append($('<option>', {
                    value: unidad.Id_Unidad,
                    text: unidad.Unidad
                }));
            });
        }, complete: function () {
            openTab(firstAvailableTab);
        },
        error: function (xhr, status, error) {
            console.error('Error al cargar unidades:', error);
            showError('Error al cargar las unidades de medida');
        }
    });
}

function cargarProductos(pestaña, selector) {
    let activos = ((pestaña === 1 || pestaña === 2) ? 0 : 1);
    showLoading();

    $.ajax({
        type: 'POST',
        url: API_BASE_URL,
        data: {action: 'consultarTabla', activos: activos},
        dataType: 'json',
        success: function (response) {
            Swal.close();
            if (response.error) {
                showError(response.error);
            } else {
                construirTabla(response, pestaña, selector);
            }
        },
        error: function (xhr, status, error) {
            Swal.close();
            showError('Error en la conexión con el servidor');
            console.error('Error:', error);
        }
    });
}

function construirTabla(data, pestaña, selector) {
    var tbody = $(selector);
    tbody.empty();

    if (data.length === 0) {
        var row = $('<tr>');
        var cell = $('<td>')
                .attr('colspan', pestaña === 2 ? '5' : '4')
                .addClass('no-records')
                .text('No hay productos registrados');
        row.append(cell);
        tbody.append(row);
    } else {
        $.each(data, function (index, producto) {
            var row = $('<tr>');
            row.append($('<td>').text(producto.nombre_Producto));
            row.append($('<td>').text(producto.unidad));
            row.append($('<td>').text(producto.cantidad));

            if (pestaña !== 3) {
                const estatusText = producto.estatus ? 'Activo' : 'Inactivo';
                const estatusClass = producto.estatus ? 'estatus-activo' : 'estatus-inactivo';
                row.append($('<td>').addClass(estatusClass).text(estatusText));
            }

            const $actionsCell = $('<td>').appendTo(row);

            if (pestaña === 1) {
                const btnText = producto.estatus ? 'Dar de Baja' : 'Activar';
                const btnClass = producto.estatus ? 'baja-btn' : 'activar-btn';

                $('<button>')
                        .addClass('action-button ' + btnClass)
                        .text(btnText)
                        .on('click', function () {
                            confirmarCambioEstatus(producto.Id_Producto, !producto.estatus, producto.nombre_Producto);
                        })
                        .appendTo($actionsCell);
            } else if (pestaña === 2) {
                $('<button>')
                        .addClass('action-button ingresar-btn')
                        .text('Ingresar')
                        .on('click', function () {
                            if (producto.estatus === 0) {
                                showWarning('Producto Inactivo', 'No se pueden realizar movimientos con este producto porque está inactivo');
                            } else {
                                mostrarModal('ingreso', producto.Id_Producto, producto.nombre_Producto, producto.Id_Unidad, producto.cantidad);
                            }
                        })
                        .appendTo($actionsCell);
            } else if (pestaña === 3) {
                $('<button>')
                        .addClass('action-button salida-btn')
                        .text('Registrar Salida')
                        .on('click', function () {
                            mostrarModal('salida', producto.Id_Producto, producto.nombre_Producto, producto.Id_Unidad, producto.cantidad);
                        })
                        .appendTo($actionsCell);
            }

            tbody.append(row);
        });
    }
}

$('#producto-form').submit(function (e) {
    e.preventDefault();
    registrarProducto();
});

function registrarProducto() {
    // Resetear mensajes de error
    $('.error-message').text('');

    // Validación del lado del cliente
    let isValid = true;
    const producto = $('#nombre-producto').val().trim();
    const unidad = $('#unidad-medida').val().trim();

    if (producto === '') {
        $('#productoError').text('El nombre del producto es requerido');
        isValid = false;
    }

    if (unidad == '0') {
        $('#unidadError').text('Por favor seleccione una opción');
        isValid = false;
    }

    if (!isValid)
        return;

    showLoading();

    $.ajax({
        type: 'POST',
        url: API_BASE_URL,
        data: {
            action: 'registrarProducto',
            producto: producto,
            unidad: unidad
        },
        dataType: 'text',
        success: function (response) {
            if (response === 'success') {
                showSuccess('Producto registrado exitosamente');
                $('#producto-form')[0].reset();
                setTimeout(function () {
                    openTab('registro-tab');
                }, 1500);

            } else {
                showError(response);
            }
        },
        error: function (xhr, status, error) {
            showError('Error en la conexión con el servidor');
            console.error('Error:', error);
        }
    });
}

function confirmarCambioEstatus(Id_Producto, estatus, nombreProducto) {
    const accion = estatus ? 'activar' : 'desactivar';

    Swal.fire({
        title: 'Confirmar acción',
        text: `¿Estás seguro que deseas ${accion} el producto "${nombreProducto}"?`,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: `Sí, ${accion}`,
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33'
    }).then((result) => {
        if (result.isConfirmed) {
            cambiarEstatusProducto(Id_Producto, estatus);
        }
    });
}

function cambiarEstatusProducto(Id_Producto, estatus) {
    showLoading();
    let estatusCambiado = (estatus ? '1' : '0');

    $.ajax({
        type: 'POST',
        url: API_BASE_URL,
        data: {
            action: 'cambiarEstatus',
            Id_Producto: Id_Producto,
            estatusCambiado: estatusCambiado
        },
        dataType: 'text',
        success: function (response) {
            if (response === 'success') {
               showSuccessWithConfirmation('Estado del producto actualizado', 'registro-tab');
            } else {
                showError(response);
            }
        },
        error: function (xhr, status, error) {
            showError('Error en la conexión con el servidor');
            console.error('Error:', error);
        }
    });
}

function mostrarModal(accion, Id_Producto, nombre, tipo, cantidad) {
    const $modal = $('#accion-modal');
    const $title = $('#modal-title');
    const $submitBtn = $('#modal-submit');

    // Configurar según la acción
    switch (accion) {
        case 'ingreso':
            $title.text('Ingreso de Producto');
            $submitBtn.text('Registrar Ingreso').removeClass().addClass('submit-button ingresar-btn').data('accion', '1');
            break;
        case 'salida':
            $title.text('Salida de Producto');
            $submitBtn.text('Registrar Salida').removeClass().addClass('submit-button salida-btn').data('accion', '0');
            break;
    }

    // Llenar datos del producto
    $('#producto-id').val(Id_Producto);
    $('#unidad-id').val(tipo);
    $('#cantidadAnterior').val(cantidad);
    $('#producto-nombre').text(nombre);
    $('#cantidad').val('');
    $('#comentario').val('');

    // Mostrar modal
    $modal.show();
}

function procesarAccionProducto() {
    // Validación del lado del cliente
    let isValid = true;
    const cantidad = $('#cantidad').val().trim();
    const cantidadAnterior = $('#cantidadAnterior').val().trim();
    const Id_Producto = $('#producto-id').val().trim();
    const unidad = $('#unidad-id').val().trim();
    const accionArealizar = $('#modal-submit').data('accion');

    if (cantidad === '') {
        showWarning('Campo requerido', 'Debes ingresar una cantidad');
        isValid = false;
    }

    if (unidad == '3' && cantidad % 1 !== 0) {
        showWarning('Valor inválido', 'No puedes introducir decimales en un producto que se mide en PIEZAS');
        isValid = false;
    }

    if (accionArealizar == '0' && parseFloat(cantidad) > parseFloat(cantidadAnterior)) {
        showWarning('Cantidad inválida', 'No puedes quitar más de lo que tienes disponible');
        isValid = false;
    }

    if (!isValid)
        return;

    showLoading();

    $.ajax({
        type: 'POST',
        url: API_BASE_URL,
        data: {
            action: 'añadir_disminuir_Cantidad',
            Id_Producto: Id_Producto,
            cantidad: cantidad,
            accionArealizar: accionArealizar
        },
        dataType: 'text',
        success: function (response) {
            if (response === 'success') {
                $('#accion-modal').hide();
                if (accionArealizar == 1) {
                    showSuccessWithConfirmation('Movimiento registrado exitosamente', 'inventario-tab');
                } else {
                    showSuccessWithConfirmation('Movimiento registrado exitosamente', 'salidas-tab');
                }
            } else {
                showError(response);
            }
        },
        error: function (xhr, status, error) {
            showError('Error en la conexión con el servidor');
            console.error('Error:', error);
        }
    });
}

// Funciones auxiliares para mostrar mensajes
function showLoading() {
    Swal.fire({
        title: 'Cargando...',
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });
}

function showSuccess(message) {
    Swal.fire({
        icon: 'success',
        title: 'Éxito',
        text: message,
        timer: 2000,
        showConfirmButton: false
    });
}

function showSuccessWithConfirmation(message, tabToOpen) {
    Swal.fire({
        icon: 'success',
        title: 'Éxito',
        text: message,
        showConfirmButton: true,
        confirmButtonText: 'Aceptar',
        timer: null,
        allowOutsideClick: false
    }).then((result) => {
        if (result.isConfirmed) {
            openTab(tabToOpen);
        }
    });
}

function showError(message) {
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: message,
        confirmButtonText: 'Entendido'
    });
}

function showWarning(title, message) {
    Swal.fire({
        icon: 'warning',
        title: title,
        text: message,
        confirmButtonText: 'Entendido'
    });
}