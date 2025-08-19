$(document).ready(function () {
    // Constantes
    const API_BASE_URL = '/Almacen/productos';
    
    // Inicialización
    init();
    
    function init() {
        consultarProductos();
        setupEventListeners();
    }
    
    function setupEventListeners() {
        // Aquí puedes agregar listeners para otros eventos si es necesario
    }
    
    function consultarProductos() {
        showLoading(true);
        
        $.ajax({
            type: 'POST',
            url: API_BASE_URL,
            data: {
                action: 'consultarTabla',
                activos: '1'
            },
            dataType: 'json',
            success: function (response) {
                showLoading(false);
                construirTabla(response);
            },
            error: function (xhr, status, error) {
                showLoading(false);
                showError('Error al cargar los productos. Por favor, intente nuevamente.');
               
            }
        });
    }
    
    function construirTabla(productos) {
        const $tbody = $('.product-table tbody');
        $tbody.empty();
        
        if (!productos || productos.length === 0) {
            mostrarMensajeSinRegistros($tbody);
            return;
        }
        
        productos.forEach(producto => {
            const $row = $('<tr>');
            
            // Asegúrate de que los nombres de las propiedades coincidan con el modelo Java
            $row.append($('<td>').text(producto.nombre_Producto || producto.Nombre_Producto));
            $row.append($('<td>').text(producto.cantidad || producto.Cantidad));
            $row.append($('<td>').text(producto.unidad || producto.Unidad));
            
            $tbody.append($row);
        });
    }
    
    function agregarBotonesAccion($row, producto) {
        // Ejemplo: agregar botones de editar/eliminar según sea necesario
        const $accionesCell = $('<td>').addClass('text-center');
        
        // Botón para cambiar estatus (activar/desactivar)
        const $estatusBtn = $('<button>')
            .addClass('btn btn-sm ' + (producto.estatus === 1 ? 'btn-danger' : 'btn-success'))
            .text(producto.estatus === 1 ? 'Desactivar' : 'Activar')
            .click(() => confirmarCambioEstatus(producto.id_Producto || producto.Id_Producto, producto.estatus, producto.nombre_Producto || producto.Nombre_Producto));
        
        $accionesCell.append($estatusBtn);
        $row.append($accionesCell);
    }
    
    function confirmarCambioEstatus(idProducto, estatusActual, nombreProducto) {
        const nuevoEstatus = estatusActual === 1 ? 0 : 1;
        const accion = nuevoEstatus === 1 ? 'activar' : 'desactivar';
        
        Swal.fire({
            title: '¿Estás seguro?',
            text: `Vas a ${accion} el producto "${nombreProducto}"`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: `Sí, ${accion}`,
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                cambiarEstatusProducto(idProducto, estatusActual);
            }
        });
    }
    
    function cambiarEstatusProducto(idProducto, estatusActual) {
        const nuevoEstatus = estatusActual === 1 ? 0 : 1;
        showLoading(true);
        
        $.ajax({
            type: 'POST',
            url: API_BASE_URL,
            data: {
                action: 'cambiarEstatus',
                Id_Producto: idProducto,
                estatusCambiado: nuevoEstatus
            },
            success: function(response) {
                showLoading(false);
                if (response === 'success') {
                    showSuccess('Estado del producto actualizado correctamente');
                    consultarProductos(); // Refrescar la tabla
                } else {
                    showError(response || 'Error al cambiar el estado del producto');
                }
            },
            error: function(xhr, status, error) {
                showLoading(false);
                showError('Error en la conexión con el servidor');
                
            }
        });
    }
    
    function mostrarMensajeSinRegistros($tbody) {
        const $row = $('<tr>');
        const $cell = $('<td>')
            .attr('colspan', '4')
            .addClass('no-records text-center py-4')
            .text('No hay productos registrados');
        
        $row.append($cell);
        $tbody.append($row);
    }
    
    function showLoading(show) {
        if (show) {
            // Puedes usar un SweetAlert como loader
            Swal.fire({
                title: 'Cargando...',
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });
        } else {
            Swal.close();
        }
    }
    
    function showError(message) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: message,
            confirmButtonText: 'Entendido'
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
});