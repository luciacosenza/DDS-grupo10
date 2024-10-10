package com.tp_anual.proyecto_heladeras_solidarias.model.contacto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class Telefono extends MedioDeContacto {
    
    @NotNull
    @Pattern(regexp = "^[1-9][0-9]{0,2}$")
    private final String codPais;

    @NotNull
    @Pattern(regexp = "^[1-9][0-9]{0,2}$")
    private final String codArea;
    
    @NotNull
    @Pattern(regexp = "^\\d{7,10}$")
    private final String numero;
    
    public Telefono(String vCodPais, String vCodArea, String vNumero) {
        codPais = vCodPais;
        codArea = vCodArea;
        numero = vNumero;
    }
}
