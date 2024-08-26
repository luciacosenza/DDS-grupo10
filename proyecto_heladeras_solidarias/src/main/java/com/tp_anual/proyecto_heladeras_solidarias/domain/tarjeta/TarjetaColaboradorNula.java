package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoNoAplica;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura.PermisoAperturaNulo;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class TarjetaColaboradorNula extends TarjetaColaborador {
    public TarjetaColaboradorNula() {
        codigo = "N/A";
        titular = null;
        estadoSolicitud = new EstadoNoAplica();
        permiso = new PermisoAperturaNulo();
    }

    @Override
    public ColaboradorHumano getTitular() {
        logger.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorNula.getTitular_err"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaColaboradorNula.getTitular_exception"));
    }

    @Override
    public EstadoSolicitud getEstadoSolicitud() {
        logger.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorNula.getEstadoSolicitud_err"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaColaboradorNula.getEstadoSolicitud_exception"));
    }


    @Override
    public PermisoApertura getPermiso() {
        return permiso;
    }

    @Override
    public void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud) {}

    @Override
    public Boolean puedeUsar() {
        return false;
    }

    @Override
    public void programarRevocacionPermisos() {}

    @Override
    public SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, SolicitudAperturaColaborador.MotivoSolicitud motivo) {
        logger.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorNula.solicitarApertura_err"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaColaboradorNula.solicitarApertura_exception"));
    }

    @Override
    public AperturaColaborador intentarApertura(HeladeraActiva heladeraAAbrir)  throws InterruptedException {
        logger.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorNula.intentarApertura_err"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaColaboradorNula.intentarApertura_exception"));
    }
}
