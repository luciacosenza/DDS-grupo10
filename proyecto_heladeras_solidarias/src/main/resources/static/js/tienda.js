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
        img: "https://noblex.com.ar/media/catalog/product/cache/c8f6a96bef9e9f64cd4973587df2520f/p/o/pop_tv_lineal_2023_1200x922_db58x7500.jpg"
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
        img: "https://atma.com.ar/media/catalog/product/cache/c8f6a96bef9e9f64cd4973587df2520f/b/m/bm8740ap.jpg"
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

function renderItems(){
    const itemsContainer = document.querySelector("#items-container");
    articulos.forEach(articulo => {
        itemsContainer.innerHTML += `<div class="card mx-0 my-5" style="width: 18rem;">
                                        <img src="${articulo.img}" class="card-img-top img-fluid h-100" alt="${articulo.articulo}">
                                        <div class="card-body h-100">
                                            <h5 class="card-title">${articulo.articulo}</h5>
                                            <p class="card-text">${articulo.precio} puntos</p>
                                            <button class="btn btn-primary" onclick="mostrarConfirmacion()">Adquirir</button>
                                        </div>
                                    </div>`

    })
}

function filtrarPor(categoria){
    const itemsContainer = document.querySelector("#items-container");
    itemsContainer.innerHTML = "";
    articulos.forEach(articulo => {
        if(articulo.categoria === categoria){
            itemsContainer.innerHTML += `<div class="card mx-0 my-5" style="width: 18rem;">
                                        <img src="${articulo.img}" class="card-img-top img-fluid h-100" alt="${articulo.articulo}">
                                        <div class="card-body h-100">
                                            <h5 class="card-title">${articulo.articulo}</h5>
                                            <p class="card-text">${articulo.precio} puntos</p>
                                            <button class="btn btn-primary" onclick="mostrarConfirmacion()> Adquirir</button>
                                        </div>
                                    </div>`
            }
        if(categoria === 'TODOS'){
            itemsContainer.innerHTML += `<div class="card mx-0 my-5" style="width: 18rem;">
                                        <img src="${articulo.img}" class="card-img-top img-fluid h-100" alt="${articulo.articulo}">
                                        <div class="card-body h-100">
                                            <h5 class="card-title">${articulo.articulo}</h5>
                                            <p class="card-text">${articulo.precio} puntos</p>
                                            <button class="btn btn-primary" onclick="mostrarConfirmacion()>Adquirir</button>
                                        </div>
                                    </div>`
        }
    })
}
renderItems();
function mostrarConfirmacion(){
    Swal.fire({
        icon: "success",
        title: "Beneficio adquirido",
        showConfirmButton: false,
        timer: 1500
    });
}

