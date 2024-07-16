package com.tp_anual_dds.domain.colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.contribuciones.ContribucionCreator;
import com.tp_anual_dds.domain.incidentes.FallaTecnica;
import com.tp_anual_dds.domain.oferta.Oferta;
import com.tp_anual_dds.domain.persona.Persona;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public abstract class Colaborador {
    protected Persona persona;
    protected Ubicacion domicilio;
    protected ArrayList<MedioDeContacto> mediosDeContacto;
    protected ArrayList<Contribucion> contribuciones;
    protected Set<Class<? extends ContribucionCreator>> creatorsPermitidos;
    protected ArrayList<Oferta> beneficiosAdquiridos;
    protected Double puntos;

    public abstract Persona getPersona();

    public Ubicacion getDomicilio() {
        return domicilio;
    }

    public ArrayList<Contribucion> getContribuciones() {
        return contribuciones;
    }

    public Double getPuntos() {
        return puntos;
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

    public Boolean esCreatorPermitido(Class<? extends ContribucionCreator> creatorClass) {
        return creatorsPermitidos.contains(creatorClass);
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

    public void darDeAlta() {
        Sistema.agregarColaborador(this);
    }

    public void darDeBaja() {
        Sistema.eliminarColaborador(this);
    }

    public void colaborar(ContribucionCreator creator, Object... args) {
        if(!esCreatorPermitido(creator.getClass())) {
            throw new IllegalArgumentException("No es una forma válida de colaborar");
        }

        Contribucion contribucion = creator.crearContribucion(this, LocalDateTime.now(), args);
        contribucion.contribuir();
        agregarContribucion(contribucion);
    }

    public void reportarFallaTecnica(String descripcion, String foto) {
        FallaTecnica fallaTecnica = new FallaTecnica(this, descripcion, foto);
        System.out.println(fallaTecnica);  // Esto es temporal, simula el registro de la fallaTecnica
    }
}