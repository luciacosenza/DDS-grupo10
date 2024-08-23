let togglePassButtons = document.querySelectorAll('.toggle-password')

function togglePassword(){
    const passwordField = this.previousElementSibling;
    const eyeIcon = this.querySelector('.toggle-pass-icon');
    
    if (passwordField.getAttribute('type') === 'password') {
        passwordField.setAttribute('type', 'text');
        eyeIcon.textContent = 'visibility_off';
    } else {
        passwordField.setAttribute('type', 'password');
        eyeIcon.textContent = 'visibility';
    }
};

togglePassButtons.forEach(button => button.addEventListener('click', togglePassword));