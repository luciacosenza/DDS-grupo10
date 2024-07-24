package com.tp_anual_dds.domain.oferta;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.sistema.Sistema;

public class Oferta {

    private String nombre;
    private Double costo;
    private Categoria categoria;
    private String imagen;
    
    public enum Categoria {
        GASTRONOMIA,
        ELECTRONICA,
        ARTICULOS_PARA_EL_HOGAR
        // Completar, de ser necesario (TODO)
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
            throw new IllegalArgumentException("No cuenta con los puntos necesarios para adquirir este beneficio");
        }
    }

    public void darDeAlta() {
        Sistema.agregarOferta(this);
    }

    public void darDeBaja() {
        Sistema.eliminarOferta(this);
    }
}