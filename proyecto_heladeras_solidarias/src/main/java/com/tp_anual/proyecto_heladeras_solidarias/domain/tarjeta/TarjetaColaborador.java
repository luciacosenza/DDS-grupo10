package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura.PermisoApertura;

public abstract class TarjetaColaborador extends Tarjeta {
    protected static final Logger logger = Logger.getLogger(TarjetaColaborador.class.getName());
    protected ColaboradorHumano titular;
    protected EstadoSolicitud estadoSolicitud;
    protected PermisoApertura permiso;

    @Override
    public abstract ColaboradorHumano getTitular();

    public abstract EstadoSolicitud getEstadoSolicitud();

    public abstract PermisoApertura getPermiso();

    public abstract void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud);

    protected abstract void programarRevocacionPermisos();

    public abstract SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, SolicitudAperturaColaborador.MotivoSolicitud motivo);

    @Override
    public abstract AperturaColaborador intentarApertura(HeladeraActiva heladeraAAbrir) throws InterruptedException;
}
