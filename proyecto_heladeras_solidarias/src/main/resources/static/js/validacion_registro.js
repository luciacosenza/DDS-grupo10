function initializeFormValidation(formSelector) {
    const form = document.querySelector(formSelector);

    if (!form) return;

    form.addEventListener('submit', event => {
        const passwordInput = form.querySelector("#password");
        const feedbackElement = passwordInput.nextElementSibling;

        feedbackElement.style.display = 'none';

        const isValidPassword = validatePassword(passwordInput.value);

        if (!isValidPassword) {
            passwordInput.setCustomValidity("Invalid field.");
            feedbackElement.style.display = 'block';
        } else {
            passwordInput.setCustomValidity("");
            feedbackElement.style.display = 'none';
        }

        if (!isValidPassword || !form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }

        form.classList.add('was-validated');
    }, false);
}

function validatePassword(password) {
    const minLength = 8;
    return password.length >= minLength;
}

function inicializarFormRegistroPersona() {
    initializeFormValidation('.register-form');
}

document.addEventListener('DOMContentLoaded', inicializarFormRegistroPersona);
