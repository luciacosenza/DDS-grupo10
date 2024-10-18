package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
public class TarjetaColaborador extends Tarjeta {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador")
    @Setter
    protected ColaboradorHumano titular;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tarjeta")
    protected List<PermisoApertura> permisos;  // final

    public TarjetaColaborador() {
        super();
    }

    public TarjetaColaborador(String vCodigo, ColaboradorHumano vTitular, List<PermisoApertura> vPermisos) {
        super(vCodigo);
        titular = vTitular;
        permisos = vPermisos;
    }

    public void agregarPermiso(PermisoApertura permiso) {
        permisos.add(permiso);
    }
}
