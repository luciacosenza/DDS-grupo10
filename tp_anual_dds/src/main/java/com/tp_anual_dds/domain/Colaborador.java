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

    public void agregarContribucion(Contribucion contribucion) {
        contribuciones.add(contribucion);
    }

    public void adquirirBeneficio(Oferta oferta) {
        beneficiosAdquiridos.add(oferta);
    }

    public <T extends MedioDeContacto> T getContacto(Class<T> tipoMedioDeContacto) {    // Con este metodo, suponemos que el Colaborador no puede tener mas de un medioDeContacto del mismo tipo
        if (!MedioDeContacto.class.isAssignableFrom(tipoMedioDeContacto)) {
            throw new IllegalArgumentException("No está buscando un medio de contacto válido");
        }

        for (MedioDeContacto contacto : mediosDeContacto) {
            if (tipoMedioDeContacto.isInstance(contacto)) {
                return tipoMedioDeContacto.cast(contacto);
            }
        }

        return null;
    }

    // darDeAlta()
    // darDeBaja()
    // modificar()

    public void colaborar(ContribucionFactory factory, Object... args) {
        Contribucion contribucion = factory.crearContribucion(this, LocalDateTime.now(), args);
        contribucion.contribuir();
        agregarContribucion(contribucion);
    }
}