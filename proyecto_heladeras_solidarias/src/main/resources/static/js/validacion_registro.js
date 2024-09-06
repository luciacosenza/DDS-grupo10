function initializeFormValidation(formSelector) {
    const form = document.querySelector(formSelector);

    if (!form) return;

    form.addEventListener('submit', event => {
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }
        form.classList.add('was-validated');
    }, false);
}

function inicializarFormRegistroPersona() {
    initializeFormValidation('.register-form');
}

document.addEventListener('DOMContentLoaded', inicializarFormRegistroPersona);