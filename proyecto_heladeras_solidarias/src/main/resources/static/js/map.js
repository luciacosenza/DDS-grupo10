const lugares = [
    {
        position: { lat: -34.603684, lng: -58.381559 },
        title: 'Heladera Obelisco',
        direccion: 'Av. 9 de Julio s/n, C1043 Cdad. Autónoma de Buenos Aires'
    },
    {
        position: { lat: -34.65896377270432, lng: -58.46725554554328},
        title: 'Heladera UTN Lugano',
        direccion: 'Mozart 2300, C1407 Cdad. Autónoma de Buenos Aires'
    },
    {
        position: {lat: -34.59928951940777, lng: -58.383902401574176},
        title: 'Heladera Teatro Colón',
        direccion: 'Tucumán 1171, C1049 Cdad. Autónoma de Buenos Aires'
    },
    {
        position: {lat: -34.54388366787101, lng: -58.4879967286211},
        title: 'Heladera Shopping Dot',
        direccion: 'Vedia 3600, C1430 Cdad. Autónoma de Buenos Aires'
    },
    {
        position: {lat:-34.61666164992063, lng:-58.49813170588592},
        title: 'Heladera All Boys',
        direccion: 'Mercedes 1951, C1407 AIK, Cdad. Autónoma de Buenos Aires'
    },
    {
        position: {lat: -34.598625070369835,  lng: -58.42011968362257},
        title: 'Heladera UTN Medrano',
        direccion: 'Av. Medrano 951, C1179AAQ Cdad. Autónoma de Buenos Aires'
    }
];


let mapa;

async function iniciarMapa(){
    const posicion = {lat: -34.5990697, lng: -58.4241739}
    const { Map } = await google.maps.importLibrary("maps");
    const { Marker } = await google.maps.importLibrary("marker");
    mapa = new Map(document.querySelector('#map'), {
        zoom:11,
        center: posicion,
        mapId: "c793049f56f6348b"
    });
    const marker = new Marker({
        position: {lat: -34.598625070369835,  lng: -58.42011968362257},
        map: mapa,
        title: 'UTN MEDRANO',
        icon: {
            url: '../assets/iconUbicacionHeladeras.png',
            scaledSize: new google.maps.Size(40, 45),
            origin: new google.maps.Point(0, 0)
        }
    });
    lugares.forEach(lugar => {
        const marker = new Marker({
            position: lugar.position,
            map: mapa,
            title: lugar.title,
            icon: {
                url: '../assets/iconUbicacionHeladeras.png',
                scaledSize: new google.maps.Size(40, 45),
                origin: new google.maps.Point(0, 0)
            }
        });

        const infoWindow = new google.maps.InfoWindow({
            content: `<h4>${lugar.title}</h4>
                        <p>${lugar.direccion}</p>`
        });

        marker.addListener('click', () => {
            infoWindow.open(mapa, marker);
        });
    });

    // Crear la InfoWindow con el contenido de la leyenda
    const infoWindow = new google.maps.InfoWindow({
        content: '<h2>Heladera 1</h2><p>Esta es la descripción de la Heladera 1.</p>'
    });

    // Agregar un evento para abrir la InfoWindow cuando se hace clic en el marcador
    marker.addListener('click', () => {
        infoWindow.open(mapa, marker);
    });

    document.querySelector('#search-button').addEventListener('click', buscarHeladeras());
}
function buscarHeladeras() {
    const query = document.querySelector('search-input').value.toLowerCase();
    const resultado = lugares.find(lugar => lugar.title.toLowerCase().includes(query));

    console.log(resultado)
    if (resultado) {
        marcador.setPosition(resultado.position);
        marcador.setTitle(resultado.title);
        const infoWindow = new google.maps.InfoWindow({
            content: resultado.content
        });
        infoWindow.open(mapa, marcador);
        mapa.setCenter(resultado.position);
        mapa.setZoom(12);
    } else {
        alert('No se encontraron heladeras que coincidan con la búsqueda.');
    }
}
iniciarMapa();
