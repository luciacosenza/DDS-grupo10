package com.tp_anual.proyecto_heladeras_solidarias.domain.documento;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Documento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private final TipoDocumento tipo;

    private final String numero;

    @Enumerated(EnumType.STRING)
    private final Sexo sexo;
    
    public enum TipoDocumento {
        DNI,
        PASAPORTE,
        LICENCIA_CONDUCIR,
        LIBRETA_CIVICA,
        LIBRETA_ENROLAMIENTO;
    }
    
    public enum Sexo {
        MASCULINO,
        FEMENINO,
        OTRO
    }
        
    public Documento(TipoDocumento vTipo, String vNumero, Sexo vSexo) {
        tipo = vTipo;
        numero = vNumero;
        sexo = vSexo;
    }
}