async function obtenerHeladeras(){
    const url = "https://ebabbd6d-dfd4-4f3d-bea8-c85e634b2a74.mock.pstmn.io/heladeras";
    const selectHeladeras = document.querySelectorAll('.select-heladeras');
    try{
        const response = await fetch(url);
        const data = await response.json();
        
        data.heladeras.forEach(heladera =>{
            selectHeladeras.forEach(select =>
                select.innerHTML += `<option value=${heladera.title}>${heladera.title}</option>`
            )
        })
    }catch(error){}
}

obtenerHeladeras()