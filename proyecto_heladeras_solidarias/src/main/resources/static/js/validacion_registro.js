function initializeFormValidation(formSelector) {
    const form = document.querySelector(formSelector);

    if (!form) return;

    form.addEventListener('submit', event => {
        const passwordInput = form.querySelector("#password");
        const feedbackElementMin = form.querySelector("#password-min-feedback");
        const feedbackElementMax = form.querySelector("#password-max-feedback");

        feedbackElementMin.style.display = 'none';
        feedbackElementMax.style.display = 'none';

        if (!validatePasswordMin(passwordInput.value)) {
            passwordInput.setCustomValidity("Invalid field.");
            feedbackElementMin.style.display = 'block';
        } else if (!validatePasswordMax(passwordInput.value)) {
            passwordInput.setCustomValidity("Invalid field.");
            feedbackElementMax.style.display = 'block';
        } else {
            passwordInput.setCustomValidity("");
            feedbackElementMin.style.display = 'none';
            feedbackElementMax.style.display = 'none';
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
