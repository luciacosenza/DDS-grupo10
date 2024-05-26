package domain;

import java.util.ArrayList;

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

    // darDeAlta()
    // darDeBaja()
    // modificar()

    public void sumarPuntos(Double puntosASumar) {
        puntos += puntosASumar;
    }

    public void colaborar( Contribucion contribucion){
        contribucion.contribuir(this);
        contribuciones.add(contribucion);
    }

    public void adquirirBeneficio(Oferta oferta) {
        beneficiosAdquiridos.add(oferta);
    }

}