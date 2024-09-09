const btnsFrecuencia = document.querySelectorAll('.card-donacion-dinero:nth-of-type(1) .option-circle');
const btnsMedioPago = document.querySelectorAll('#medioPago .option-circle');
const btnsMonto = document.querySelectorAll('#monto .option-circle');
const inputMontoPersonalizado = document.getElementById('montoPersonalizado');
const btnOtroImporte = document.getElementById('otroImporte');
const btnDonar = document.getElementById('btnDonar');

// Guardar selección
let seleccionFrecuencia = null;
let seleccionMedioPago = null;
let seleccionMonto = null;

// Función para seleccionar un botón y abrir la pestaña siguiente
function seleccionarBotonFrecuencia() {
    btnsFrecuencia.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            validarUnicidad(btnsFrecuencia);
            if (!btn.classList.contains('selected')) {
                btn.className = 'option-circle selected';
                seleccionFrecuencia = btn.textContent.trim();
                mostrarSeccionMedioPago();
                verificarSeleccionCompleta();
            }
        });
    });
}

function seleccionarBotonMedioPago() {
    btnsMedioPago.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            validarUnicidad(btnsMedioPago);
            if (!btn.classList.contains('selected')) {
                btn.className = 'option-circle selected';
                seleccionMedioPago = btn.textContent.trim();
                mostrarSeccionMonto();
                verificarSeleccionCompleta();
            }
        });
    });
}

function seleccionarBotonMonto() {
    btnsMonto.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            if (btn.id === 'otroImporte') {
                inputMontoPersonalizado.classList.remove('input-monto');
                inputMontoPersonalizado.focus();
            } else {
                validarUnicidad(btnsMonto);
                btn.className = 'option-circle selected';
                seleccionMonto = btn.textContent.trim();
                inputMontoPersonalizado.classList.add('input-monto');
            }
            verificarSeleccionCompleta();
        });
    });
}

function validarUnicidad(btns) {
    btns.forEach(btn => {
        btn.className = 'option-circle';
    });
}

function mostrarSeccionMedioPago() {
    const medioPagoCard = document.getElementById('medioPago');
    medioPagoCard.classList.remove('oculto');
    medioPagoCard.scrollIntoView({ behavior: 'smooth' });
}

function mostrarSeccionMonto() {
    const montoCard = document.getElementById('monto');
    montoCard.classList.remove('oculto');
    montoCard.scrollIntoView({ behavior: 'smooth' });
}

function verificarSeleccionCompleta() {
    if (seleccionFrecuencia && seleccionMedioPago && (seleccionMonto || inputMontoPersonalizado.value)) {
        btnDonar.classList.remove('oculto');
    } else {
        btnDonar.classList.add('oculto');
    }
}

seleccionarBotonFrecuencia();
seleccionarBotonMedioPago();
seleccionarBotonMonto();

inputMontoPersonalizado.addEventListener('input', function() {
    seleccionMonto = inputMontoPersonalizado.value;
    verificarSeleccionCompleta();
});
