package com.tp_anual.proyecto_heladeras_solidarias.domain.oferta;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class Oferta {
    private static final Logger logger = Logger.getLogger(Oferta.class.getName());
    private String nombre;
    private Double costo;
    private Categoria categoria;
    private String imagen;
    
    public enum Categoria {
        GASTRONOMIA,
        ELECTRONICA,
        ARTICULOS_PARA_EL_HOGAR
        // TODO: Completar, de ser necesario
    }

    public Oferta(String vNombre, Double vCosto, Categoria vCategoria, String vImagen) {
        nombre = vNombre;
        costo = vCosto;
        categoria = vCategoria;
        imagen = vImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getCosto() {
        return costo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void validarPuntos(Colaborador colaborador) {
        Double puntosColaborador = colaborador.getPuntos();
        
        if (puntosColaborador < costo) {
            logger.log(Level.SEVERE, I18n.getMessage("oferta.Oferta.validarPuntos_err", colaborador.getPersona().getNombre(2), colaborador.getPuntos(), costo, nombre));
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