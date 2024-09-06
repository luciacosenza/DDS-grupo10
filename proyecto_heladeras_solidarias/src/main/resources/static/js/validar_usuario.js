let tipo_usuario;

if(document.querySelector("#iniciar-sesion")){
    const btn_login = document.querySelector("#btn-login");
    btn_login.addEventListener("click", function(e){
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

if(document.querySelector("#index")){
    const tipo_usuario_guardado = sessionStorage.getItem('usuario');
    if(tipo_usuario_guardado == 'admin'){
        renderAdmin()
    }
    if(tipo_usuario_guardado == 'colHumano'){
        renderColHumano()
    }
    if(tipo_usuario_guardado == 'colJuridico'){
        renderColJuridico()
    }     
}

function renderAdmin(){
    navbar.innerHTML = ` <div class="container-fluid">
                                <a class="navbar-brand" href="#">
                                    <img src="./assets/logo_negro.png" alt="Logo" height="50">
                                </a>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarNav">
                                    <ul class="navbar-nav ms-auto">
                                        <li class="nav-item">
                                            <a class="nav-link active" aria-current="page" href="#">Inicio</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/migracion_datos.html">Migrar archivo</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/alertas.html">Alertas</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="#">Reportes</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/quienes_somos.html">Quiénes somos</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/como_participar.html">Cómo participar</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/contacto.html">Contacto</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/colaborar.html">Colaborar</a>
                                        </li>
                                    </ul>
                                <div class="d-flex flex-column ms-lg-3">
                                    <span class="icon-perfil material-symbols-outlined">
                                        account_circle
                                    </span>
                                    admin
                                </div>
                            </div>
                        </div>`
}

function renderColHumano(){
    navbar.innerHTML = ` <div class="container-fluid">
                                <a class="navbar-brand" href="#">
                                    <img src="./assets/logo_negro.png" alt="Logo" height="50">
                                </a>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarNav">
                                    <ul class="navbar-nav ms-auto">
                                        <li class="nav-item">
                                            <a class="nav-link active" aria-current="page" href="#">Inicio</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/quienes_somos.html">Quiénes somos</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/como_participar.html">Cómo participar</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/contacto.html">Contacto</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/colaborar_personas_fisicas.html">Colaborar</a>
                                        </li>
                                    </ul>
                                <div class="d-flex flex-column align-items-center ms-lg-3">
                                    <span class="icon-perfil material-symbols-outlined">
                                        account_circle
                                    </span>
                                </div>
                            </div>
                        </div>`
}

function renderColJuridico(){
    navbar.innerHTML = ` <div class="container-fluid">
                                <a class="navbar-brand" href="#">
                                    <img src="./assets/logo_negro.png" alt="Logo" height="50">
                                </a>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarNav">
                                    <ul class="navbar-nav ms-auto">
                                        <li class="nav-item">
                                            <a class="nav-link active" aria-current="page" href="#">Inicio</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/quienes_somos.html">Quiénes somos</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/como_participar.html">Cómo participar</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/contacto.html">Contacto</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="./pages/colaborar_personas_juridicas.html">Colaborar</a>
                                        </li>
                                    </ul>
                                <div class="d-flex flex-column align-items-center ms-lg-3">
                                    <span class="icon-perfil material-symbols-outlined">
                                        account_circle
                                    </span>
                                </div>
                            </div>
                        </div>`
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