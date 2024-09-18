package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoExpirada;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoPosible;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoRealizada;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura.PermisoAperturaActivo;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
public class TarjetaColaboradorActiva extends TarjetaColaborador {
    public TarjetaColaboradorActiva(ColaboradorHumano vTitular) {
        super(GeneradorCodigo.generarCodigo(true), vTitular, new EstadoPosible(), new PermisoAperturaActivo());
    }

    @Override
    public Boolean puedeUsar() {
        return true; // Se puede definir alguna lógica específica si es necesario
    }

    @Override
    protected void programarRevocacionPermisos() {
        Integer delay = 3;
        TimeUnit unidad = TimeUnit.HOURS;
        
        Runnable revocacionPermisos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime fechaOtorgamiento = permiso.getFechaOtorgamiento();
            long horasPasadas = ChronoUnit.HOURS.between(fechaOtorgamiento, ahora);
            if (horasPasadas >= 3) {
                permiso.resetHeladeraPermitida();
                setEstadoSolicitud(new EstadoExpirada());
            }

            log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.programarRevocacionPermisos_info", permiso.getHeladeraPermitida().getNombre(), titular.getPersona().getNombre(2)));
        };

        // Programa la tarea para que se ejecute una vez después de 3 horas
        timer.schedule(revocacionPermisos, delay, unidad);
    }

    // Este método se ejecuta siempre que un Colaborador quiera solicitar la Apertura de una Heladera (generalmente para una Donación de Vianda, un retiro de lote para una Distribución de Viandas o un depósito de lote para una Distribución de Viandas)
    @Override
    public SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, MotivoSolicitud motivo) {
        estadoSolicitud.manejar(this);
        // A partir de acá, el Estado de Solicitud pasará a Pendiente, hasta que se de de alta la Solicitud de Apertura
        
        heladeraInvolucrada.getGestorDeAperturas().revisarSolicitudApertura(motivo);

        // Registramos la Solicitud de Apertura en el Sistema
        SolicitudAperturaColaborador solicitudApertura = new SolicitudAperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, this.getTitular(), motivo);
        solicitudApertura.darDeAlta();

        estadoSolicitud = new EstadoRealizada();
        // A partir de acá, el Estado de Solicitud pasará a Realizada, hasta que se revoquen los permisos, pasadas las 3 horas

        // Actualizo (añado) los permisos correspondientes a la Heladera involucrada
        permiso.setHeladeraPermitida(heladeraInvolucrada);
        permiso.actualizarFechaOtorgamiento();

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.solicitarApertura_info", heladeraInvolucrada.getNombre(), titular.getPersona().getNombre(2)));

        programarRevocacionPermisos();

        return solicitudApertura;
    }

    // Este método se ejecuta siempre que un Colaborador quiera realizar la Apertura de una Heladera (generalmente para una Donación de Vianda, un retiro de lote para una Distribución de Viandas o un depósito de lote para una Distribución de Viandas)
    @Override
    public AperturaColaborador intentarApertura(HeladeraActiva heladeraInvolucrada) {
        // Primero chequeo internamente que pueda realizar la Apertura
        heladeraInvolucrada.getGestorDeAperturas().revisarPermisoAperturaC(titular);

        // Registramos la Apertura en el Sistema
        AperturaColaborador apertura = new AperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, this.getTitular());
        apertura.darDeAlta();
        
        // Reseteo el Estado de Solicitud
        estadoSolicitud = new EstadoPosible();
        // A partir de acá, el Estado de Solicitud pasará a Posible, hasta que se solicite una nueva Apertura

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.intentarApertura_info", heladeraInvolucrada.getNombre(), titular.getPersona().getNombre(2)));

        return apertura;
    }
}
