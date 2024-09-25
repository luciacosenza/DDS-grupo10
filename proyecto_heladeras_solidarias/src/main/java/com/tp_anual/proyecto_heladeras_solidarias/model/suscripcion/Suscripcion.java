package com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_suscripcion")
@Log
@Getter
@Setter
public abstract class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador_id")
    @NotNull
    private final ColaboradorHumano colaborador;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    @NotNull
    private final HeladeraActiva heladera;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "medio_de_contacto_id")
    @NotNull
    private MedioDeContacto medioDeContactoElegido; // El Colaborador elige por qué Medio de Contacto ser notificado sobre cuestiones de la Suscripción

    public enum CondicionSuscripcion {
        VIANDAS_MIN,
        VIANDAS_MAX,
        DESPERFECTO
    }

    protected Suscripcion(ColaboradorHumano vColaborador, HeladeraActiva vHeladera, MedioDeContacto vMedioDeContactoElegido) {
        colaborador = vColaborador;
        heladera = vHeladera;
        medioDeContactoElegido = vMedioDeContactoElegido;
    }
    
    public void darDeAlta() {
        Sistema.getGestorDeSuscripciones().agregarSuscripcion(this);
    }

    public void darDeBaja() {
        Sistema.getGestorDeSuscripciones().eliminarSuscripcion(this);
    }
}