package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@Log
public abstract class PermisoApertura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    protected Heladera heladeraPermitida;

    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime fechaOtorgamiento;

    public abstract Heladera getHeladeraPermitida();

    public abstract void actualizarFechaOtorgamiento();

    public abstract Boolean esHeladeraPermitida(HeladeraActiva heladera);

    public abstract void resetHeladeraPermitida();
}