package com.tp_anual.proyecto_heladeras_solidarias.domain.contacto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class Telegram extends MedioDeContacto{
    
    private final String numero;    // TODO: Pensar si tiene que contener un Telefono o repetimos los atributos de Telefono
    
    public Telegram(String vNumero) {
        numero = vNumero;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
