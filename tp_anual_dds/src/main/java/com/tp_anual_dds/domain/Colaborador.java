package com.tp_anual_dds.domain;

import java.util.ArrayList;
import java.time.LocalDateTime;

public abstract class Colaborador {
    protected ArrayList<MedioDeContacto> mediosDeContacto;
    protected Ubicacion domicilio;
    protected ArrayList<Contribucion> contribuciones;
    protected ArrayList<Oferta> beneficiosAdquiridos;
    protected Double puntos;

    public Ubicacion getDomicilio() {
        return domicilio;
    }

    public Double getPuntos() {
        return puntos;
    }

    public void sumarPuntos(Double puntosASumar) {
        puntos += puntosASumar;
    }

    public void adquirirBeneficio(Oferta oferta) {
        beneficiosAdquiridos.add(oferta);
    }

    // darDeAlta()
    // darDeBaja()
    // modificar()

    public void colaborar(ContribucionFactory factory, Object... args) {
        Contribucion contribucion = factory.crearContribucion(this, LocalDateTime.now(), args);
        contribucion.contribuir();
        contribuciones.add(contribucion);
    }

}