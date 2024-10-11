package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;


import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Getter
@Setter
public abstract class TarjetaColaborador extends Tarjeta {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador_id")
    protected ColaboradorHumano titular;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "permiso_apertura_id")
    protected ArrayList<PermisoApertura> permisos;

    protected TarjetaColaborador(String vCodigo, ColaboradorHumano vTitular, ArrayList<PermisoApertura> vPermisos) {
        super(vCodigo);
        titular = vTitular;
        permisos = vPermisos;
    }

    public abstract void agregarPermiso(PermisoApertura permiso);

    public abstract void eliminarPermiso(PermisoApertura permiso);
}
