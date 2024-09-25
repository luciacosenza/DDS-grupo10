package com.tp_anual.proyecto_heladeras_solidarias.model.oferta;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Log
@Getter
@Setter
public class Oferta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    @NotBlank
    private String nombre;

    @NotNull
    @Min(value = 0)
    private Double costo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Categoria categoria;

    private String imagen;
    
    public enum Categoria {
        GASTRONOMIA,
        ELECTRONICA,
        ARTICULOS_PARA_EL_HOGAR
    }

    public Oferta(String vNombre, Double vCosto, Categoria vCategoria, String vImagen) {
        nombre = vNombre;
        costo = vCosto;
        categoria = vCategoria;
        imagen = vImagen;
    }

    public void validarPuntos(Colaborador colaborador) {
        Double puntosColaborador = colaborador.getPuntos();
        
        if (puntosColaborador < costo) {
            log.log(Level.SEVERE, I18n.getMessage("oferta.Oferta.validarPuntos_err", colaborador.getPersona().getNombre(2), colaborador.getPuntos(), costo, nombre));
            throw new UnsupportedOperationException(I18n.getMessage("oferta.Oferta.validarPuntos_exception"));
        }
    }

    public void darDeAlta() {
        Sistema.agregarOferta(this);
    }

    public void darDeBaja() {
        Sistema.eliminarOferta(this);
    }
}