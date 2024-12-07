package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;

@Log
public class TarjetaColaboradorNula extends TarjetaColaborador {

    public TarjetaColaboradorNula() {
        super(null, null, new ArrayList<>());
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public ColaboradorHumano getTitular() {
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage = messageSource.getMessage("tarjeta.TarjetaColaboradorNula.getTitular_err", null, Locale.getDefault());
        String exceptionMessage = messageSource.getMessage("tarjeta.TarjetaColaboradorNula.getTitular_exception", null, Locale.getDefault());

        log.log(Level.SEVERE, logMessage);
        throw new UnsupportedOperationException(exceptionMessage);
    }

    @Override
    public List<PermisoApertura> getPermisos() {
        return permisos;
    }

    @Override
    public void setTitular(ColaboradorHumano vTitular) {}

    @Override
    public void agregarPermiso(PermisoApertura permiso) {}
}
