function initializeFormValidation(formSelector) {
    const form = document.querySelector(formSelector);

    if (!form) return;

    form.addEventListener('submit', event => {
        const passwordInput = form.querySelector("#password");
        const feedbackElement = passwordInput.nextElementSibling;

        feedbackElement.style.display = "none";

        if (!validatePasswordMin(passwordInput.value)) {
            passwordInput.setCustomValidity("Invalid field.");
            feedbackElement.textContent = 'La contraseña debe tener como mínimo 8 caracteres';
            feedbackElement.style.display = 'block';
        } else if (!validatePasswordMax(passwordInput.value)) {
            passwordInput.setCustomValidity("Invalid field.");
            feedbackElement.textContent = 'La contraseña debe tener 32 caracteres como máximo ';
            feedbackElement.style.display = 'block';
        } else {
            passwordInput.setCustomValidity("");
            feedbackElement.style.display = 'none';
        }

        if (!validatePassword(passwordInput.value) || !form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }

        form.classList.add('was-validated');
    }, false);
}

function validatePasswordMin(password) {
    const minLength = 8;
    return password.length >= minLength;
}

function validatePasswordMax(password) {
    const maxLength = 32;
    return password.length <= maxLength;
}

function validatePassword(password) {
    return validatePasswordMin(password) && validatePasswordMax(password);
}

function inicializarFormRegistroPersona() {
    initializeFormValidation('.register-form');
}

document.addEventListener('DOMContentLoaded', inicializarFormRegistroPersona);
