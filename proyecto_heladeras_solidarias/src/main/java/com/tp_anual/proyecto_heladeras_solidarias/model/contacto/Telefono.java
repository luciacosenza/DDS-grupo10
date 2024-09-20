package com.tp_anual.proyecto_heladeras_solidarias.model.contacto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class Telefono extends MedioDeContacto {
    
    private final String codPais;

    private final String codArea;
    
    private final String numero;
    
    public Telefono(String vCodPais, String vCodArea, String vNumero) {
        codPais = vCodPais;
        codArea = vCodArea;
        numero = vNumero;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
