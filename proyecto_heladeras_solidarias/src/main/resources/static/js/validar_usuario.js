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
                title: "Sesion iniciada como administrador",
                confirmButtonText: "Continuar a inicio",
                }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = "../index.html";
                }
                });
            }
        })
    }

if(document.querySelector("#index")){
    const tipo_usuario_guardado = sessionStorage.getItem('usuario');
    let navbar = document.querySelector(".navbar");
    if(tipo_usuario_guardado == 'admin'){
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
                                    <span class="material-symbols-outlined">
                                        account_circle
                                    </span>
                                    admin
                                </div>
                            </div>
                        </div>`
    }
}