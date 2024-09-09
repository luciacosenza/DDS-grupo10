const articulos = [
    {
        id: 1,
        categoria: "ARTICULOS_PARA_EL_HOGAR",
        articulo: "Aspiradora",
        precio: "50",
        img: "https://medias.musimundo.com/medias/00035055-172634-172634-01.png-172634-01.png-size515?context=bWFzdGVyfGltYWdlc3w2NTkzNDB8aW1hZ2UvcG5nfGFERmxMMmhoWmk4eE1ETTNPRFl3TWpJMU1ESTNNQzh3TURBek5UQTFOUzB4TnpJMk16UXRNVGN5TmpNMFh6QXhMbkJ1WnkweE56STJNelJmTURFdWNHNW5YM05wZW1VMU1UVXw5ZTkwYWQ2YWMzMGIzOTVmODY5ZTAzZTkwYWMzMzgyNzg1MWI1ZGJiMGRmMTBmZmNlZDRlZTAxODUwMmM1MmFl"
    },
    {
        id: 2,
        categoria: "GASTRONOMIA",
        articulo: "Cafetera",
        precio: "30",
        img: "https://png.pngtree.com/png-clipart/20240227/original/pngtree-coffee-maker-png-image_14434212.png"
    },
    {
        id: 3,
        categoria: "ELECTRONICA",
        articulo: "Televisor",
        precio: "300",
        img: "https://medias.musimundo.com/medias/size1200-149482-01.jpg?context=bWFzdGVyfGltYWdlc3w5NzgxM3xpbWFnZS9qcGVnfGFHUTJMMmd5TVM4eE1EVTJPRFl5T1RBNU1qTTRNaTl6YVhwbE1USXdNRjh4TkRrME9ESmZNREV1YW5Cbnw1NjQ4YjcxNGE1NGQ1ZWRiMjdlNzZkNjc2NmU3YWE1ODQyNTBmNmRjNzVlZDc0ODk4ZmEyYmYzZWQzYjExMjVl"
    },
    {
        id: 4,
        categoria: "ARTICULOS_PARA_EL_HOGAR",
        articulo: "Licuadora",
        precio: "40",
        img: "https://electroluxar.vtexassets.com/arquivos/ids/157807-800-800?v=637063110902270000&width=800&height=800&aspect=true"
    },
    {
        id: 5,
        categoria: "GASTRONOMIA",
        articulo: "Tostadora",
        precio: "25",
        img: "https://novogar.com.ar/public/images/productos/producto_4373_1.png?rnd=1346280030"
    },
    {
        id: 6,
        categoria: "ELECTRONICA",
        articulo: "Auriculares",
        precio: "20",
        img: "https://images.bidcom.com.ar/resize?src=https://www.bidcom.com.ar/publicacionesML/productos/ABLUE108/1000x1000-ABLUE108.jpg&w=500&q=100"
    },
    {
        id: 7,
        categoria: "ARTICULOS_PARA_EL_HOGAR",
        articulo: "Ventilador",
        precio: "35",
        img: "https://www.torca.com.ar/images/00000000000632695431663269.jpg"
    },
    {
        id: 8,
        categoria: "GASTRONOMIA",
        articulo: "Batidora",
        precio: "45",
        img: "https://electroluxar.vtexassets.com/arquivos/ids/162195/StandMixer_EKM40_Perspective_Electrolux_1000x1000.png?v=637934170908270000"
    },
    {
        id: 9,
        categoria: "ELECTRONICA",
        articulo: "Smartphone",
        precio: "250",
        img: "https://zetaelectronica.com.ar/wp-content/uploads/2021/10/iphone-13-dual-sim-esim-512gb-5g-roz-6gb-ram_10074468_4_1631708844.jpeg"
    },
    {
        id: 10,
        categoria: "ARTICULOS_PARA_EL_HOGAR",
        articulo: "Plancha",
        precio: "30",
        img: "https://images.fravega.com/f500/5042cd6db83d30f793cae209535547b9.jpg"
    }
];

const itemsContainer = document.querySelector('#items-container');
const puntos = document.querySelector('.puntos');
const btnsAdquirir = document.querySelectorAll('.btn-adquirir');

let puntosUsuario = 500 // provisorio

function renderItems(unosArticulos) {
    itemsContainer.innerHTML = '';
    unosArticulos.forEach(articulo => {
        itemsContainer.innerHTML += `<div class="card mx-0 my-5" style="width: 18rem;">
                                        <img src="${articulo.img}" class="card-img-top img-fluid h-100" alt="${articulo.articulo}">
                                        <div class="card-body h-100">
                                            <h5 class="card-title">${articulo.articulo}</h5>
                                            <p class="card-text"><span class="precio">${articulo.precio}</span> puntos</p>
                                            <button class="btn btn-primary btn-adquirir" onclick="adquirir(${articulo.precio})">Adquirir</button>
                                        </div>
                                    </div>`
    })
    puntos.innerHTML = '';
    puntos.innerHTML = `Puntos: ${puntosUsuario}`;
}

function filtrarPor(categoria) {
    const filtro = []
    articulos.forEach(articulo => {
    if(articulo.categoria === categoria) {
        filtro.push(articulo)
    }
    renderItems(filtro);
    })
    if (categoria === 'TODOS') renderItems(articulos) ;
}

renderItems(articulos);

const categoriaSelect = document.querySelector(".categorias-tienda-container");
categoriaSelect.addEventListener("click", (e) => {
    if (e.target.classList.contains("category")) {
        const categoria = e.target.id;
        filtrarPor(categoria);
        }
        })

        function adquirir(precio) {
            const precioArticulo = parseInt(precio); // Asegurarse de que el precio sea un número
        
            if (puntosUsuario >= precioArticulo) {
                puntosUsuario -= precioArticulo; // Restar los puntos
                Swal.fire( {
                    icon: "success",
                    title: "Beneficio adquirido",
                    showConfirmButton: false,
                    timer: 1500
                });
            } else {
                Swal.fire( {
                    icon: "error",
                    title: "Puntos insuficientes",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        
            // Actualizar los puntos en la página
            puntos.innerHTML = `Puntos: ${puntosUsuario}`;
        }      

