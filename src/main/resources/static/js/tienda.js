document.addEventListener("DOMContentLoaded", function () {
    const btnsCategoria = document.querySelectorAll(".category");
    const itemsContainer = document.querySelector("#items-container");
    const puntos = document.querySelector(".puntos");

    let puntosUsuario = parseInt(puntos.textContent.split(":")[1]);

    btnsCategoria.forEach(btn => {
        btn.addEventListener("click", () => {
            const selectedCategory = btn.id;

            document.querySelectorAll("#items-container .card").forEach(item => {
                const itemCategory = item.getAttribute("data-categoria");

                if (selectedCategory === "TODOS" || itemCategory === selectedCategory) {
                    item.style.display = "block";
                } else {
                    item.style.display = "none";
                }
            });
        });
    });

    itemsContainer.addEventListener("click", async function (e) {
        if (e.target.classList.contains("btn-adquirir")) {
            const button = e.target;
            const card = button.closest(".card");
            const precioArticulo = parseInt(card.querySelector(".precio").textContent);
            const idArticulo = button.getAttribute("data-id");

            if (puntosUsuario >= precioArticulo) {

                try {
                    const url = `/tienda/obtener-oferta/${idArticulo}`;
                    const response = await fetch(url)

                    if (response.ok) {
                        Swal.fire({
                            icon: "success",
                            title: "Beneficio adquirido",
                            showConfirmButton: false,
                            timer: 1500
                        }).then(() =>
                        {
                            location.reload(true)
                        })
                    } else {
                        const errorText = await response.text();
                        Swal.fire({
                            icon: "error",
                            title: "Error",
                            text: errorText,
                            showConfirmButton: true
                        });
                    }
                } catch (error) {
                    Swal.fire({
                        icon: "error",
                        title: "Error de conexión",
                        text: "No se pudo completar la adquisición.",
                        showConfirmButton: true
                    });
                }
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Puntos insuficientes",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        }
    });

});
