package com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

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
    @JoinColumn(name = "colaborador")
    private final ColaboradorHumano colaborador;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera")
    private final Heladera heladera;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "medio_de_contacto")
    private MedioDeContacto medioDeContactoElegido; // El Colaborador elige por qué Medio de Contacto ser notificado sobre cuestiones de la Suscripción

    public enum CondicionSuscripcion {
        VIANDAS_MIN,
        VIANDAS_MAX,
        DESPERFECTO
    }

    protected Suscripcion(ColaboradorHumano vColaborador, Heladera vHeladera, MedioDeContacto vMedioDeContactoElegido) {
        colaborador = vColaborador;
        heladera = vHeladera;
        medioDeContactoElegido = vMedioDeContactoElegido;
    }
}