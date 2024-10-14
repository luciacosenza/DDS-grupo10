package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.util.ArrayList;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class TarjetaColaboradorNula extends TarjetaColaborador {
    public TarjetaColaboradorNula() {
        super("N/A", null, new ArrayList<>());
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public ColaboradorHumano getTitular() {
        log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorNula.getTitular_err"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaColaboradorNula.getTitular_exception"));
    }

    @Override
    public ArrayList<PermisoApertura> getPermisos() {
        return permisos;
    }

    @Override
    public void setTitular(ColaboradorHumano vTitular) {}

    @Override
    public void setPermisos(ArrayList<PermisoApertura> vPermisosApertura) {}

    @Override
    public Boolean puedeUsar() {
        return false;
    }

    @Override
    public void agregarPermiso(PermisoApertura permiso) {}
}
