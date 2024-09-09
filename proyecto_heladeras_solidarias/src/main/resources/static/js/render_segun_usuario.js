const btnLogin =  document.querySelector('.btn-login');
const btnRegister =  document.querySelector('.btn-register');
const iconPerfil = document.querySelector('.icon-perfil');
const textUser = document.querySelector('.text-user');
const hrefColab = document.querySelectorAll('.href-colab');


function esIndex(){
    return document.querySelector("#index");
}

function renderSegunColaborador() {
    const tipoUsuarioGuardado = sessionStorage.getItem('usuario');

    if(tipoUsuarioGuardado){
        btnLogin.style.display = 'none';
        btnRegister.style.display = 'none'
        iconPerfil.style.display = 'block';
        textUser.style.display = 'block';
        if(tipoUsuarioGuardado== 'colHumano'){
            hrefColab.forEach(link => link.href = esIndex() ? './pages/colaborar_personas_fisicas.html' : './colaborar_personas_fisicas.html');
            if(esIndex()) {
                const hrefTienda = document.querySelector('.href-tienda');
                const hrefFalla = document.querySelector('.href-falla');
                const hrefSusHeladera = document.querySelector('.href-suscribirse-heladera')
                hrefTienda.href = './pages/tienda.html';
                hrefFalla.href = './pages/reportar_falla_tecnica.html';
                hrefSusHeladera.href = './pages/suscribirse.html';
            }
        }
        
        if(tipoUsuarioGuardado == 'colJuridico'){
            hrefColab.forEach(link => link.href = esIndex() ? './pages/colaborar_personas_juridicas.html' : './colaborar_personas_juridicas.html');
            if(esIndex()) {
                const hrefTienda = document.querySelector('.href-tienda');
                const hrefFalla = document.querySelector('.href-falla');
                const hrefSusHeladera = document.querySelector('.href-suscribirse-heladera')
                hrefSusHeladera.style.display = 'none'
                hrefTienda.href = './pages/tienda.html';
                hrefFalla.href = './pages/reportar_falla_tecnica.html';
            }
        }
    } else {
        btnLogin.style.display = 'block';
        btnRegister.style.display = 'block';
        iconPerfil.style.display = 'none';
        textUser.style.display = 'none';
    }
}

renderSegunColaborador()



