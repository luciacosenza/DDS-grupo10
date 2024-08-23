const button_submit = document.querySelector(".submit-registro");

button_submit.addEventListener("click", function(e) {
    e.preventDefault();
    let resultadoDiv = document.getElementById("resultado");

    let alturaCalle = document.getElementById("altura").value.trim();
    if (isNaN(alturaCalle)) {
        resultadoDiv.textContent = 'Por favor, ingrese una altura válida.';
        resultadoDiv.style.color = 'red';
        return;
    }

    let prefijo = document.getElementById("prefijo").value.trim();
    let codigoArea = document.getElementById("codigo-area").value.trim();
    let numeroTelefono = document.getElementById("numero-telefono").value.trim();

    // Validar prefijo (debe empezar con un "+" y tener entre 1 y 4 dígitos)
    if (!/^\+\d{1,4}$/.test(prefijo)) {
        resultadoDiv.textContent = 'Por favor, ingrese un prefijo válido (Ej: +54).';
        resultadoDiv.style.color = 'red';
        return;
    }

    // Validar código de área (debe ser un número de 1 a 3 dígitos)
    if (!/^\d{1,3}$/.test(codigoArea)) {
        resultadoDiv.textContent = 'Por favor, ingrese un código de área válido (Ej: 9).';
        resultadoDiv.style.color = 'red';
        return;
    }

    // Validar número de teléfono (debe ser un número de 8 dígitos)
    if (!/^\d{8}$/.test(numeroTelefono)) {
        resultadoDiv.textContent = 'Por favor, ingrese un número de teléfono válido de 8 dígitos (Ej: 12345678).';
        resultadoDiv.style.color = 'red';
        return;
    }

    // Si todo es válido, redirigir a la página de crear usuario
    window.location.href = "./crear_usuario.html";
});