async function recomendarPuntosHeladeras(){
    const url = "https://69cf53c9-48f7-431e-b667-601a587589a8.mock.pstmn.io/recommendation";
    try{
        const response = await fetch(url);
        const data = await response.json();
        
        const { Marker } = await google.maps.importLibrary("marker");
        data.puntosRecomendados.forEach(punto =>{
                    const puntoRecomendado = new Marker({
                        position: {lat: punto.latitud, lng: punto.longitud},
                        map: mapa,
                        title: "Punto recomendado",
                        icon: {
                            url: '../assets/iconRecomendacionHeladeras.png',
                            scaledSize: new google.maps.Size(30, 35),
                            origin: new google.maps.Point(0, 0)
                        }
                    })
                    const infoWindow = new google.maps.InfoWindow({
                        content: `<h4>Punto Recomendado</h4>
                                    <p></p>` //! Faltaria agregar un nombre y dirección al lugar
                    });
            
                    puntoRecomendado.addListener('click', () => {
                        infoWindow.open(mapa, puntoRecomendado);
                    });
                })
    }catch(error){}
}

async function abrirMapa() {
    if (document.querySelector('#colocar-heladera')){
        const btnAbrirMapa = document.querySelector('#btn-abrir-mapa');
        btnAbrirMapa.addEventListener('click', async () => {
        
        });
    }    
}

async function iniciarMapa() {
            const posicion = {lat: -34.5990697, lng: -58.4241739}
            const { Map } = await google.maps.importLibrary("maps");
            mapa = new Map(document.querySelector('.map-recomendacion'), {
                zoom:11,
                center: posicion,
                mapId: "c793049f56f6348b" 
            });
}
        document.querySelector('#btn-abrir-mapa').addEventListener("click", function(e) {
            e.preventDefault();
            let mapDisplay = document.querySelector(".map-container").style.display
            document.querySelector(".map-container").style.display = (mapDisplay == 'none')? 'block': 'none';
            iniciarMapa()
            recomendarPuntosHeladeras()
            if (!Map) {
                iniciarMapa();
            }})