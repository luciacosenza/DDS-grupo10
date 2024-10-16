package com.tp_anual.proyecto_heladeras_solidarias.model.contacto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class WhatsApp extends MedioDeContacto {

    private String numero;    // TODO: Pensar si tiene que contener un Telefono o repetimos los atributos de Telefono

    public WhatsApp() {}

    public WhatsApp(String vNumero) {
        numero = vNumero;
    }
}
