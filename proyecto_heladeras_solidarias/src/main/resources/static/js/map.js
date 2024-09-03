async function obtenerHeladeras(){
    const url = "https://ebabbd6d-dfd4-4f3d-bea8-c85e634b2a74.mock.pstmn.io/heladeras";
    try{
        const response = await fetch(url);
        const data = await response.json();
        
        const { Marker } = await google.maps.importLibrary("marker");
        data.heladeras.forEach(heladera =>{
                    const posicionHeladera = new Marker({
                        position: heladera.position,
                        map: mapa,
                        title: "Heladera",
                        icon: {
                            url: '../assets/iconUbicacionHeladeras.png',
                            scaledSize: new google.maps.Size(30, 35),
                            origin: new google.maps.Point(0, 0)
                        }
                    })
                    const infoWindow = new google.maps.InfoWindow({
                        content: `<h4>${heladera.title}</h4>
                                    <p>${heladera.direccion}</p>` 
                    });
            
                    posicionHeladera.addListener('click', () => {
                        infoWindow.open(mapa, posicionHeladera);
                    });
                })
    }catch(error){}
}

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

    obtenerHeladeras();

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