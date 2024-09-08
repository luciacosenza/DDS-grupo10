document.querySelector('.cerrar-sesion').addEventListener('click', cerrarSesion);

function cerrarSesion(){
    localStorage.removeItem('usuario');
    window.location.href = 'index.html';
}