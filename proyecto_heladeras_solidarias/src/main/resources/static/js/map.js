let mapa;

async function iniciarMapa(){
    const posicion = {lat: -34.5990697, lng: -58.4241739}
    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
    mapa = new Map(document.querySelector('#map'), {
        zoom:10,
        center: posicion,
        mapId: "MapaHeladeras"
    });
    const marker = new AdvancedMarkerElement({
        position: {lat: -34.598625070369835,  lng: -58.42011968362257},
        map: mapa,
        title: 'UTN MEDRANO',
    });


    // Crear la InfoWindow con el contenido de la leyenda
    const infoWindow = new google.maps.InfoWindow({
        content: '<h2>Heladera 1</h2><p>Esta es la descripción de la Heladera 1.</p>'
    });

    // Agregar un evento para abrir la InfoWindow cuando se hace clic en el marcador
    marker.addListener('click', () => {
        infoWindow.open(mapa, marker);
    });

}

iniciarMapa();
