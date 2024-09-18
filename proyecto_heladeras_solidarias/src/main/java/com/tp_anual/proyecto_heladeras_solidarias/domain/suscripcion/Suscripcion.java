package com.tp_anual.proyecto_heladeras_solidarias.domain.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
@Getter
@Setter
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador_id")
    private final ColaboradorHumano colaborador;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    private final HeladeraActiva heladera;

    private Integer viandasDisponiblesMin;

    private Integer viandasParaLlenarMax;

    private Boolean notificarDesperfecto;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medio_de_contacto_id")
    private MedioDeContacto medioDeContactoElegido; // El Colaborador elige por qué Medio de Contacto ser notificado sobre cuestiones de la Suscripción

    public enum CondicionSuscripcion {
        VIANDAS_MIN,
        VIANDAS_MAX,
        DESPERFECTO
    }

    public Suscripcion(ColaboradorHumano vColaborador, HeladeraActiva vHeladera, Integer vViandasDisponiblesMin, Integer vViandasParaLlenarMax, Boolean vNotificarDesperfecto, MedioDeContacto vMedioDeContactoElegido) {
        colaborador = vColaborador;
        heladera = vHeladera;
        viandasDisponiblesMin = vViandasDisponiblesMin;
        viandasParaLlenarMax = vViandasParaLlenarMax;
        notificarDesperfecto = vNotificarDesperfecto;
        medioDeContactoElegido = vMedioDeContactoElegido;
    }
    
    public void darDeAlta() {
        Sistema.getGestorDeSuscripciones().agregarSuscripcion(this);
    }

    public void darDeBaja() {
        Sistema.getGestorDeSuscripciones().eliminarSuscripcion(this);
    }
}