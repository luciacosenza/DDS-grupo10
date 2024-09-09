let tipo_usuario;
const usuarioIncorrecto = document.querySelector('.invalid-user');

usuarioIncorrecto.style.display = 'none';


    const formInicio = document.querySelector(".register-form");
    formInicio.addEventListener("submit", function(e) {
        e.preventDefault();
        const usuario = document.querySelector("#usuario");
        const password = document.querySelector("#password");
        const usuarioValue = usuario.value;
        const passwordValue = password.value;

        //! lo de abajo es provisorio hasta que podamos diferenciar entre tipos de usuarios
        if(usuarioValue == 'admin' && passwordValue == 'admin') {
            tipo_usuario = 'admin';
            sessionStorage.setItem('usuario', tipo_usuario);
            Swal.fire( {
                title: "Sesión iniciada como administrador",
                confirmButtonText: "Continuar a inicio",
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "./admin.html"
                    }
                });
            }
        else if (usuarioValue == 'colHumano' && passwordValue == 'colHumano') {
            tipo_usuario = 'colHumano';
            sessionStorage.setItem('usuario', tipo_usuario);
            Swal.fire( {
                title: "Sesión iniciada como colaborador humano",
                confirmButtonText: "Continuar a inicio",
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "../index.html";
                    }
                });
            }
        else if (usuarioValue == 'colJuridico' && passwordValue == 'colJuridico') {
            tipo_usuario = 'colJuridico';
            sessionStorage.setItem('usuario', tipo_usuario);
            Swal.fire( {
                title: "Sesión iniciada como colaborador jurídico",
                confirmButtonText: "Continuar a inicio",
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "../index.html";
                    }
                });
            }
        else {
            usuarioIncorrecto.style.display = 'block';
        }
        })


