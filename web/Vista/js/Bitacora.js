$(document).ready(function() {
    // Constantes
    const API_BASE_URL = '/Almacen/bitacora';
    const BITACORA_FILTERS = {
        ALL: 'all',
        INGRESO: 'ingreso',
        SALIDA: 'salida'
    };
    
    // Inicialización
    initBitacora();
    
    function initBitacora() {
        loadBitacora(BITACORA_FILTERS.ALL);
        setupBitacoraEventListeners();
    }
    
    function setupBitacoraEventListeners() {
        // Manejar clic en botones de filtro
        $(document).on('click', '.filter-btn', function() {
            const $btn = $(this);
            const filter = $btn.data('filter');
            
            $('.filter-btn').removeClass('active');
            $btn.addClass('active');
            
            loadBitacora(filter);
        });
    }
    
    function loadBitacora(filter = BITACORA_FILTERS.ALL) {
        showLoading(true);
        
        $.ajax({
            type: 'POST',
            url: API_BASE_URL,
            data: { 
                action: 'consultarBitacora',
                filtro: filter === BITACORA_FILTERS.ALL ? '' : filter
            },
            dataType: 'json',
            success: function(response) {
                buildBitacoraTable(response);
                showLoading(false);
            },
            error: function(xhr, status, error) {
                showLoading(false);
                showBitacoraError('No se pudieron cargar los datos de la bitácora');
                console.error('Error al cargar bitácora:', error);
            }
        });
    }
    
    function buildBitacoraTable(movimientos) {
        const $tbody = $('.bitacora-table tbody');
        $tbody.empty();
        
        if (!movimientos || movimientos.length === 0) {
            showNoRecordsMessage($tbody);
            return;
        }
        
        movimientos.forEach(movimiento => {
            const movimientoType = movimiento.tipo_Movimiento || movimiento.Tipo_Movimiento;
            const isIngreso = movimientoType.toLowerCase().includes('ingreso');
            
            const $row = $('<tr>').addClass(isIngreso ? 'movimiento-ingreso' : 'movimiento-salida');
            
            // Usamos ambas formas de propiedades por compatibilidad
            $row.append($('<td>').text(movimiento.Producto || movimiento.Nombre_Producto || 'N/A'));
            $row.append($('<td>').text(movimiento.Usuario || movimiento.Nombre_Usuario || 'N/A'));
            
            const $typeCell = $('<td>');
            $typeCell.append($('<span>').addClass('movimiento-icon').text(isIngreso ? '↑' : '↓'));
            $typeCell.append(' ' + movimientoType);
            $row.append($typeCell);
            
            // Formatear cantidad a 2 decimales
            const cantidad = movimiento.cantidad_Movimiento || movimiento.Cantidad_Movimiento || 0;
            $row.append($('<td>').text(cantidad.toFixed(2)));
            
            $row.append($('<td>').text(movimiento.fecha_Movimiento || movimiento.Fecha || 'N/A'));
            $row.append($('<td>').text(movimiento.hora_Movimiento || movimiento.Hora || 'N/A'));
            
            $tbody.append($row);
        });
    }
    
    function showNoRecordsMessage($tbody) {
        $tbody.append(
            $('<tr>').append(
                $('<td>')
                    .attr('colspan', '6')
                    .addClass('text-center py-3')
                    .text('No hay registros en la bitácora')
            )
        );
    }
    
    function showLoading(show) {
        if (show) {
            $('#loader').fadeIn();
        } else {
            $('#loader').fadeOut();
        }
    }
    
    function showBitacoraError(message) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: message,
            confirmButtonText: 'Entendido'
        });
    }
});