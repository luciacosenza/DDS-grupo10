package com.tp_anual.proyecto_heladeras_solidarias.model.colaborador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorNula;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@DiscriminatorValue("Humano")
@Log
@Getter
public class ColaboradorHumano extends Colaborador {    // Implementa una Interfaz "ColaboradorHumanoObserver" a nivel conceptual

    @OneToOne(mappedBy = "titular", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    protected TarjetaColaborador tarjeta;

    @OneToMany(mappedBy = "colaborador", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    protected ArrayList<Suscripcion> suscripciones;   // Será una Suscripción por Heladera

    public ColaboradorHumano() {
        super();
    }

    public ColaboradorHumano(User vUsuario, PersonaFisica vPersona, Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos) {
        super(vUsuario, vPersona, vDomicilio, vMediosDeContacto, vContribuciones, vBeneficiosAdquiridos, vPuntos);
        tarjeta = new TarjetaColaboradorNula();
        suscripciones = new ArrayList<>();
    }

    @Override
    public PersonaFisica getPersona() {
        return (PersonaFisica) persona;
    }

    public TarjetaColaborador getTarjeta() {
        return tarjeta != null ? tarjeta : new TarjetaColaboradorNula();    // Sin importar si estamos en el ciclo normal de vida del objeto, o si hicimos una recuperación desde la base de datos, si el Colaborador no cuenta con una TarjetaColaborador, el getter retornará una TarjetaColaboradorNula
    }

    public void agregarTarjeta(TarjetaColaborador tarjetaColaborador) {
        setTarjeta(tarjetaColaborador);
    }

    public void agregarSuscripcion(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }

    public void eliminarSuscripcion(Suscripcion suscripcion) {
        suscripciones.remove(suscripcion);
    }
}