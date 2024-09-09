let mapa;
let marker;  
let infoWindowActual;  
let heladeras = []; 

async function iniciarMapa() {
    const posicion = { lat: -34.5990697, lng: -58.4241739 };

    const { Map } = await google.maps.importLibrary("maps");
    const { Marker } = await google.maps.importLibrary("marker");
    const { Autocomplete } = await google.maps.importLibrary("places");
    const { spherical } = await google.maps.importLibrary("geometry");

    mapa = new Map(document.querySelector('#map'), {
        zoom: 11,
        center: posicion,
        mapId: "c793049f56f6348b"
    });

    marker = new Marker({
        map: mapa,
        title: "Posición de búsqueda",
        visible: false  
    });

    heladeras = await obtenerHeladeras(); 

    let autocomplete = document.querySelector('#autocomplete');
    const search = new Autocomplete(autocomplete);
    search.bindTo('bounds', mapa);

    search.addListener('place_changed', function () {
        const place = search.getPlace();
        if (!place.geometry || !place.geometry.location) {
            window.alert("Error al mostrar el lugar");
            return;
        }

        if (infoWindowActual) {
            infoWindowActual.close();
        }

        marker.setPosition(place.geometry.location);
        marker.setVisible(true);  // Mostrar el marcador
        mapa.setCenter(place.geometry.location);
        mapa.setZoom(14);

        const heladeraMasCerca = encontrarHeladeraMasCercana(place.geometry.location, heladeras, spherical);
        
        if (heladeraMasCerca) {
            const infoWindow = new google.maps.InfoWindow({
                content: `<h4>Heladera más cercana</h4><p>${heladeraMasCerca.marker.getTitle()}</p>`
            });

            infoWindow.open(mapa, heladeraMasCerca.marker);
            infoWindowActual = infoWindow;  
        }
    });
}

function encontrarHeladeraMasCercana(userPosition, heladeras, spherical) {
    let minDistancia = Infinity;
    let heladeraMasCercana = null;

    heladeras.forEach(heladera => {
        // Calcular la distancia entre la posición del usuario y cada heladera
        const distancia = spherical.computeDistanceBetween(
            userPosition, 
            new google.maps.LatLng(heladera.position)
        );

        if (distancia < minDistancia) {
            minDistancia = distancia;
            heladeraMasCercana = heladera;
        }
    });

    return heladeraMasCercana;
}

async function obtenerHeladeras() {
    const url = "https://ebabbd6d-dfd4-4f3d-bea8-c85e634b2a74.mock.pstmn.io/heladeras";
    try {
        const response = await fetch(url);
        const data = await response.json();

        const { Marker } = await google.maps.importLibrary("marker");
        let heladeras = []; 

        data.heladeras.forEach(heladera => {
            const marker = new Marker({
                position: heladera.position,
                map: mapa,
                title: heladera.title,
                icon: {
                    url: '../assets/iconUbicacionHeladeras.png',
                    scaledSize: new google.maps.Size(25, 35),
                    origin: new google.maps.Point(0, 0)
                }
            });

            const infoWindow = new google.maps.InfoWindow({
                content: `<h4>${heladera.title}</h4><p>${heladera.direccion}</p>`
            });

            marker.addListener('click', () => {
                infoWindow.open(mapa, marker);
            });

            heladeras.push({ marker: marker, position: heladera.position });
        });

        return heladeras; 
    } catch (error) {
        console.error('Error fetching heladeras data:', error);
    }
}
iniciarMapa()