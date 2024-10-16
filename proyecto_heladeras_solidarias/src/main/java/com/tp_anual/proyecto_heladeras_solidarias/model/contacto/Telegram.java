package com.tp_anual.proyecto_heladeras_solidarias.model.contacto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class Telegram extends MedioDeContacto{
    
    private String numero;    // TODO: Pensar si tiene que contener un Telefono o repetimos los atributos de Telefono

    public Telegram() {}

    public Telegram(String vNumero) {
        numero = vNumero;
    }
}
