let tipo_usuario;

if(document.querySelector("#iniciar-sesion")){
    const formInicio = document.querySelector(".register-form");
    formInicio.addEventListener("submit", function(e){
        e.preventDefault();
        const usuario = document.querySelector("#usuario");
        const password = document.querySelector("#password");
        const usuarioValue = usuario.value;
        const passwordValue = password.value;

        //! lo de abajo es provisorio hasta que podamos diferenciar entre tipos de usuarios
        if(usuarioValue == 'admin' && passwordValue == 'admin'){
            tipo_usuario = 'admin';
            sessionStorage.setItem('usuario', tipo_usuario)
            Swal.fire({
                title: "Sesión iniciada como administrador",
                confirmButtonText: "Continuar a inicio",
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "../index.html";
                    }
                });
            }
        if (usuarioValue == 'colHumano' && passwordValue == 'colHumano'){
            tipo_usuario = 'colHumano';
            sessionStorage.setItem('usuario', tipo_usuario)
            Swal.fire({
                title: "Sesión iniciada como colaborador humano",
                confirmButtonText: "Continuar a inicio",
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "../index.html";
                    }
                });
            }
        if (usuarioValue == 'colJuridico' && passwordValue == 'colJuridico'){
            tipo_usuario = 'colJuridico';
            sessionStorage.setItem('usuario', tipo_usuario)
            Swal.fire({
                title: "Sesion iniciada como colaborador jurídico",
                confirmButtonText: "Continuar a inicio",
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "../index.html";
                    }
                });
            }
        })
    }

let navbar = document.querySelector(".navbar");
let nombrePaginaActual;
if(document.querySelector("#index")){
    nombrePaginaActual = "index"
    const tipo_usuario_guardado = sessionStorage.getItem('usuario');
    if(tipo_usuario_guardado == 'admin'){
        renderNavbarAdmin()
    }
    if(tipo_usuario_guardado == 'colHumano'){
        renderColHumano(nombrePaginaActual)
    }
    if(tipo_usuario_guardado == 'colJuridico'){
        renderColJuridico(nombrePaginaActual)
    }     
}

if(document.querySelector("#quienes-somos")){
    console.log("Entre a quienes somos");
    nombrePaginaActual = "quienes_somos"
    const tipo_usuario_guardado = sessionStorage.getItem('usuario');
    if(tipo_usuario_guardado == 'admin'){
        renderNavbarAdmin()
    }
    if(tipo_usuario_guardado == 'colHumano'){
        renderColHumano(nombrePaginaActual)
    }
    if(tipo_usuario_guardado == 'colJuridico'){
        renderColJuridico(nombrePaginaActual)
    }     
}

if(document.querySelector("#como-participar")){
    console.log("Entre a como participar");
    nombrePaginaActual = "como_participar"
    const tipo_usuario_guardado = sessionStorage.getItem('usuario');
    if(tipo_usuario_guardado == 'admin'){
        renderNavbarAdmin()
    }
    if(tipo_usuario_guardado == 'colHumano'){
        renderColHumano(nombrePaginaActual)
    }
    if(tipo_usuario_guardado == 'colJuridico'){
        renderColJuridico(nombrePaginaActual)
    }     
}

function renderNavbarAdmin() {
    const onlyAdminFeatures = document.querySelectorAll('.admin-only'); // paginas solo para administrador
    const colab = document.querySelector('.href-colab'); // colaborar
    const defaultPages = document.querySelectorAll('.default-pages'); // quienes somos y como colaborar
    const loginBtn = document.querySelector('.btn-login'); // boton de inicio de sesion
    const registerBtn = document.querySelector('.btn-register'); // boton de registro
    const textUser = document.querySelector('.text-user'); // el nombre que aparece abajo del icono de perfil

    onlyAdminFeatures.forEach(element => {
        element.style.display = 'block';
        });
    colab.style.display = 'none';
    defaultPages.forEach(element => {
        element.style.display = 'none'
        });
    loginBtn.style.display = 'none';
    registerBtn.style.display = 'none';
    textUser.textContent = 'Admin'
    /*console.log("Renderizando navbar para Admin");
    
    console.log("nombrePaginaActual:", nombrePaginaActual);
    let esIndex = nombrePaginaActual.includes("index");
    console.log("esIndex:", esIndex);

    const basePath = esIndex ? "./" : "../";
    console.log("Base path para Admin:", basePath);

    navbar.innerHTML = ` <div class="container-fluid">
                                <a class="navbar-brand" href="#">
                                    <img src="${basePath}assets/logo_negro.png" alt="Logo" height="50">
                                </a>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarNav">
                                    <ul class="navbar-nav ms-auto">
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}index.html">Inicio</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/migracion_datos.html">Migrar archivo</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/alertas.html">Alertas</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/reportes.html">Reportes</a>
                                        </li>
                                    </ul>
                                    <div class="d-flex flex-column ms-lg-3">
                                        <span class="icon-perfil material-symbols-outlined">
                                            account_circle
                                        </span>
                                        admin
                                    </div>
                                </div>
                            </div>`;

    const linksNavegacion = document.querySelectorAll('.nav-link');
    linkActivo(linksNavegacion, nombrePaginaActual);*/
}

function renderColHumano(nombrePaginaActual) {
    console.log("Renderizando navbar para Colaborador Humano");
    
    console.log("nombrePaginaActual:", nombrePaginaActual);
    let esIndex = nombrePaginaActual.includes("index");
    console.log("esIndex:", esIndex);

    const basePath = esIndex ? "./" : "../";
    console.log("Base path para Admin:", basePath);

    navbar.innerHTML = ` <div class="container-fluid">
                                <a class="navbar-brand" href="#">
                                    <img src="${basePath}assets/logo_negro.png" alt="Logo" height="50">
                                </a>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarNav">
                                    <ul class="navbar-nav ms-auto">
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}index.html">Inicio</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/quienes_somos.html">Quiénes somos</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/como_participar.html">Cómo participar</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/colaborar_personas_fisicas.html">Colaborar</a>
                                        </li>
                                    </ul>
                                    <div class="d-flex flex-column align-items-center ms-lg-3">
                                        <span class="icon-perfil material-symbols-outlined">
                                            account_circle
                                        </span>
                                    </div>
                                </div>
                            </div>`;
    const linksNavegacion = document.querySelectorAll('.nav-link');
    linkActivo(linksNavegacion, nombrePaginaActual);
}

function renderColJuridico(nombrePaginaActual) {
    console.log("Renderizando navbar para Colaborador Juridico");
    
    console.log("nombrePaginaActual:", nombrePaginaActual);
    let esIndex = nombrePaginaActual.includes("index");
    console.log("esIndex:", esIndex);

    const basePath = esIndex ? "./" : "../";
    console.log("Base path para Admin:", basePath);

    navbar.innerHTML = ` <div class="container-fluid">
                                <a class="navbar-brand" href="#">
                                    <img src="${basePath}assets/logo_negro.png" alt="Logo" height="50">
                                </a>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarNav">
                                    <ul class="navbar-nav ms-auto">
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}index.html">Inicio</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/quienes_somos.html">Quiénes somos</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/como_participar.html">Cómo participar</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${basePath}pages/colaborar_personas_juridicas.html">Colaborar</a>
                                        </li>
                                    </ul>
                                    <div class="d-flex flex-column align-items-center ms-lg-3">
                                        <span class="icon-perfil material-symbols-outlined">
                                            account_circle
                                        </span>
                                    </div>
                                </div>
                            </div>`;
                            
    const linksNavegacion = document.querySelectorAll('.nav-link');
    linkActivo(linksNavegacion, nombrePaginaActual);
}

function linkActivo(linksNavegacion, nombrePaginaActual) {
    console.log("Activando el link correspondiente a la página:", nombrePaginaActual);
    
    linksNavegacion.forEach(link => {
        const href = link.getAttribute('href');
        console.log("Verificando link:", href);

        if (href.includes(nombrePaginaActual)) {
            console.log(`Marcando como activo el link: ${href}`);
            link.classList.add('active');
            link.setAttribute('aria-current', 'page');
            link.setAttribute('href', '#'); // Cambiar href a '#' para la página activa
        } else {
            link.classList.remove('active');
            link.removeAttribute('aria-current');
            link.setAttribute('href', href); // Mantener el href original en las demás páginas
        }
    });
}

// Validación personalizada de Bootstrap
function validar() {
    'use strict';
    const forms = document.querySelectorAll('.needs-validation');

    Array.from(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
        
            form.classList.add('was-validated');
        }, false);
    });
}

validar();