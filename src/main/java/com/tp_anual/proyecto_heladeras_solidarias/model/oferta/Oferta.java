package com.tp_anual.proyecto_heladeras_solidarias.model.oferta;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

@Entity
@Log
@Getter
public class Oferta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Setter
    private String nombre;

    @Min(value = 0)
    @Setter
    private Double costo;

    @Enumerated(EnumType.STRING)
    @Setter
    private Categoria categoria;

    @Setter
    private String imagen;

    public enum Categoria {
        GASTRONOMIA,
        ELECTRONICA,
        ARTICULOS_PARA_EL_HOGAR
    }

    public Oferta() {}

    public Oferta(String vNombre, Double vCosto, Categoria vCategoria, String vImagen) {
        nombre = vNombre;
        costo = vCosto;
        categoria = vCategoria;
        imagen = vImagen;
    }
}