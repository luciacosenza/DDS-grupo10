package com.tp_anual_dds.domain.colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
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
    protected Contribucion contribucionPendiente;   // Esto podria ser plural en un futuro
    protected ArrayList<Oferta> beneficiosAdquiridos;
    protected Double puntos;

    public abstract Persona getPersona();

    public Ubicacion getDomicilio() {
        return domicilio;
    }

    public ArrayList<Contribucion> getContribuciones() {
        return contribuciones;
    }

    public Contribucion getContribucionPendiente() {
        return contribucionPendiente;
    }

    public ArrayList<Oferta> getBeneficiosAdquiridos() {
        return beneficiosAdquiridos;
    }

    public Double getPuntos() {
        return puntos;
    }

    public <T extends MedioDeContacto> T getContacto(Class<T> tipoMedioDeContacto) {    // Con este metodo, suponemos que el Colaborador no puede tener mas de un medioDeContacto del mismo tipo
        for (MedioDeContacto contacto : mediosDeContacto) {
            if (tipoMedioDeContacto.isInstance(contacto)) {
                return tipoMedioDeContacto.cast(contacto);
            }
        }

        throw new NoSuchElementException("El colaborador no cuenta con ese medio de contacto");
    }

    public void setContribucionPendiente(Contribucion vContribucionPendiente) {
        contribucionPendiente = vContribucionPendiente;
    }

    public Boolean esCreatorPermitido(Class<? extends ContribucionCreator> creatorClass) {
        return creatorsPermitidos.contains(creatorClass);
    }

    public void sumarPuntos(Double puntosASumar) {
        puntos += puntosASumar;
    }

    public void agregarContacto(MedioDeContacto contacto) {
        mediosDeContacto.add(contacto);
    }

    public void agregarContribucion(Contribucion contribucion) {
        contribuciones.add(contribucion);
    }

    public void agregarBeneficio(Oferta oferta) {
        beneficiosAdquiridos.add(oferta);
    }

    public void darDeAlta() {
        Sistema.agregarColaborador(this);
    }

    public void darDeBaja() {
        Sistema.eliminarColaborador(this);
    }

    public Contribucion colaborar(ContribucionCreator creator, LocalDateTime fechaContribucion, Object... args) {
        if(!esCreatorPermitido(creator.getClass())) {
            throw new IllegalArgumentException("No es una forma válida de colaborar");
        }

        Contribucion contribucion = creator.crearContribucion(this, fechaContribucion, args);
        contribucion.contribuir();
        setContribucionPendiente(contribucion);

        return contribucion;
    }

    public void confirmarContribucion(Contribucion contribucion) {
        contribucion.confirmar();
        agregarContribucion(contribucion);
    }

    public void intentarAdquirirBeneficio(Oferta oferta) {
        oferta.validarPuntos(this);
        oferta.darDeBaja();
        agregarBeneficio(oferta);
    } 

    public void reportarFallaTecnica(String descripcion, String foto) {
        FallaTecnica fallaTecnica = new FallaTecnica(this, descripcion, foto);
        System.out.println(fallaTecnica);  // Esto es temporal, simula el registro de la fallaTecnica
    }
}