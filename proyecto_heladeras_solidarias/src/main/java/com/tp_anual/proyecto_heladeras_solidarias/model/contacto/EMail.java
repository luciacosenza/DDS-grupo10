package com.tp_anual.proyecto_heladeras_solidarias.model.contacto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class EMail extends MedioDeContacto {
    
    @Email
    private String direccionCorreo; // final

    public EMail() {
        super();
    }

    public EMail(String vDireccionCorreo) {
        direccionCorreo = vDireccionCorreo;
    }
}
