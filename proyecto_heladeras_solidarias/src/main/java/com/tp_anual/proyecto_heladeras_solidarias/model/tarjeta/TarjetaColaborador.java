package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Getter
@Setter
public class TarjetaColaborador extends Tarjeta {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador")
    protected ColaboradorHumano titular;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tarjeta")
    protected ArrayList<PermisoApertura> permisos;

    public TarjetaColaborador(String vCodigo, ColaboradorHumano vTitular, ArrayList<PermisoApertura> vPermisos) {
        super(vCodigo);
        titular = vTitular;
        permisos = vPermisos;
    }

    @Override
    public Boolean puedeUsar() {
        return true; // Se puede definir alguna lógica específica si es necesario
    }

    public void agregarPermiso(PermisoApertura permiso) {
        permisos.add(permiso);
    }

    public void eliminarPermiso(PermisoApertura permiso) {
        permisos.remove(permiso);
    }
}
