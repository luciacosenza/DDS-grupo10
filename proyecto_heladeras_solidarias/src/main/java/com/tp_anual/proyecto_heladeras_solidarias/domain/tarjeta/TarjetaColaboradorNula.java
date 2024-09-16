package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoNoAplica;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoPosible;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura.PermisoAperturaActivo;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura.PermisoAperturaNulo;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class TarjetaColaboradorNula extends TarjetaColaborador {
    public TarjetaColaboradorNula() {
        super("N/A", null, new EstadoNoAplica(), new PermisoAperturaNulo());
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
    public EstadoSolicitud getEstadoSolicitud() {
        log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorNula.getEstadoSolicitud_err"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaColaboradorNula.getEstadoSolicitud_exception"));
    }

    @Override
    public PermisoApertura getPermiso() {
        return permiso;
    }

    @Override
    public void setTitular(ColaboradorHumano vTitular) {}

    @Override
    public void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud) {}

    @Override
    public void setPermiso(PermisoApertura vPermisoApertura) {}

    @Override
    public Boolean puedeUsar() {
        return false;
    }

    @Override
    protected void programarRevocacionPermisos() {}

    @Override
    public SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, SolicitudAperturaColaborador.MotivoSolicitud motivo) {
        log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorNula.solicitarApertura_err"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaColaboradorNula.solicitarApertura_exception"));
    }

    @Override
    public AperturaColaborador intentarApertura(HeladeraActiva heladeraAAbrir)  throws InterruptedException {
        log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorNula.intentarApertura_err"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaColaboradorNula.intentarApertura_exception"));
    }
}
