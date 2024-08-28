async function recomendarPuntosHeladeras(){
    const url = "https://69cf53c9-48f7-431e-b667-601a587589a8.mock.pstmn.io/recommendation";
    const response = await fetch(url);
    const data = await response.json();
    //const puntosRecomendados = data.i
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
    console.log(data);
    /*data.forEach(punto =>{
                const puntoRecomendado = new AdvancedMarkerElement({
                    position: punto,
                    map: mapa,
                    title: "Punto recomendado"
                })})*/
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
                zoom:10,
                center: posicion,
                mapId: "MapaHeladeras"
            });
}
        document.querySelector('#btn-abrir-mapa').addEventListener("click", function(e) {
            e.preventDefault()
            // Mostrar el contenedor del mapa
            document.querySelector(".map-recomendacion").style.display = "block";
            iniciarMapa()
            recomendarPuntosHeladeras()
            // Inicializar el mapa (solo si no ha sido inicializado antes)
            if (!Map) {
                iniciarMapa();
            }})