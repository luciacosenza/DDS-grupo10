async function obtenerHeladeras() {
    const url = "https://ebabbd6d-dfd4-4f3d-bea8-c85e634b2a74.mock.pstmn.io/heladeras";
    try {
        const response = await fetch(url);
        const data = await response.json();

        const { Marker } = await google.maps.importLibrary("marker");
        data.heladeras.forEach(heladera => {
            const posicionHeladera = new Marker({
                position: heladera.position,
                map: mapa,
                title: "Heladera",
                icon: {
                    url: '../assets/iconUbicacionHeladeras.png',
                    scaledSize: new google.maps.Size(30, 35),
                    origin: new google.maps.Point(0, 0)
                }
            });
            
            const infoWindow = new google.maps.InfoWindow({
                content: `<h4>${heladera.title}</h4>
                          <p>${heladera.direccion}</p>`
            });

            posicionHeladera.addListener('click', () => {
                infoWindow.open(mapa, posicionHeladera);
            });
        });
    } catch (error) {
        console.error('Error fetching heladeras data:', error);
    }
}

let mapa;
let marker;  // Definir el marcador globalmente

async function iniciarMapa() {
    const posicion = { lat: -34.5990697, lng: -58.4241739 };
    const { Map } = await google.maps.importLibrary("maps");
    const { Marker } = await google.maps.importLibrary("marker");
    const { PlacesService, Autocomplete } = await google.maps.importLibrary("places"); 
    // Inicializar el mapa
    mapa = new Map(document.querySelector('#map'), {
        zoom: 11,
        center: posicion,
        mapId: "c793049f56f6348b"
    });

    // Crear un marcador inicial
    marker = new Marker({
        position: posicion,
        map: mapa,
        title: "Posición inicial"
    });

    obtenerHeladeras();

    // Crear la InfoWindow con contenido de ejemplo
    const infoWindow = new google.maps.InfoWindow({
        content: '<h2>Heladera 1</h2><p>Esta es la descripción de la Heladera 1.</p>'
    });

    // Agregar evento para abrir la InfoWindow cuando se hace clic en el marcador
    marker.addListener('click', () => {
        infoWindow.open(mapa, marker);
    });

    // Seleccionar el campo de autocompletado
    let autocomplete = document.querySelector('#autocomplete');
    const search = new Autocomplete(autocomplete);
    search.bindTo('bounds', mapa);

    // Agregar el evento para manejar el lugar seleccionado en el autocomplete
    search.addListener('place_changed', function () {
        infoWindow.close();
        marker.setVisible(false);

        const place = search.getPlace();
        if (!place.geometry || !place.geometry.location) {
            window.alert("Error al mostrar el lugar");
            return;
        }

        // Ajustar el mapa al lugar encontrado
        if (place.geometry.viewport) {
            mapa.fitBounds(place.geometry.viewport);
        } else {
            mapa.setCenter(place.geometry.location);
            mapa.setZoom(17);
        }

        // Colocar el marcador en el nuevo lugar
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);
    });
}

iniciarMapa();