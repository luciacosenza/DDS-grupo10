document.querySelector('.cerrar-sesion').addEventListener('click', cerrarSesion);

function esIndex(){
    return document.querySelector('#index');
}

function cerrarSesion(){
    sessionStorage.removeItem('usuario');
    window.location.href = esIndex() ? './index.html' : '../index.html';
}
