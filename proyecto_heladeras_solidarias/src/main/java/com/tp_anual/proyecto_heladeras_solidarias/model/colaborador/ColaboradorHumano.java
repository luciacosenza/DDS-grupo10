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

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@DiscriminatorValue("Humano")
@Log
@Getter
@Setter
public class ColaboradorHumano extends Colaborador {    // Implementa una Interfaz "ColaboradorHumanoObserver" a nivel conceptual

    @OneToOne(mappedBy = "titular", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    protected TarjetaColaborador tarjeta;
    
    @OneToMany(mappedBy = "colaborador", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    protected final ArrayList<Suscripcion> suscripciones;   // Será una Suscripción por Heladera

    public ColaboradorHumano(PersonaFisica vPersona, Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos) {
        super(vPersona, vDomicilio, vMediosDeContacto, vContribuciones, vBeneficiosAdquiridos, vPuntos);
        tarjeta = new TarjetaColaboradorNula();
        suscripciones = new ArrayList<>();
    }

    @Override
    public PersonaFisica getPersona() {
        return (PersonaFisica) persona;
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