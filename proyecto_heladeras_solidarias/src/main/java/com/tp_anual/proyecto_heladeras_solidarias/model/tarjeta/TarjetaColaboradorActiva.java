package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.GeneradorCodigo;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
public class TarjetaColaboradorActiva extends TarjetaColaborador {
    
    public TarjetaColaboradorActiva(ColaboradorHumano vTitular) {
        super(GeneradorCodigo.generarCodigo(true), vTitular, new ArrayList<>());
    }

    @Override
    public Boolean puedeUsar() {
        return true; // Se puede definir alguna lógica específica si es necesario
    }

    @Override
    public void agregarPermiso(PermisoApertura permiso) {
        permisos.add(permiso);
    }

    @Override
    public void eliminarPermiso(PermisoApertura permiso) {
        permisos.remove(permiso);
    }

    @Override
    public void programarRevocacionPermisos(PermisoApertura permiso) {
        HeladeraActiva heladeraInvolucrada = permiso.getHeladeraPermitida();
        Integer tiempoPermiso = heladeraInvolucrada.getTiempoPermiso();
        TimeUnit unidadTiempoPermiso = heladeraInvolucrada.getUnidadTiempoPermiso();

        Runnable revocacionPermisos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime fechaOtorgamiento = permiso.getFechaOtorgamiento();
            long horasPasadas = ChronoUnit.HOURS.between(fechaOtorgamiento, ahora);
            if (horasPasadas >= 3) {
                permiso.setOtorgado(false);
            }

            log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.programarRevocacionPermisos_info", permiso.getHeladeraPermitida().getNombre(), titular.getPersona().getNombre(2)));
        };

        // Programa la tarea para que se ejecute una vez después de 3 horas
        timer.schedule(revocacionPermisos, tiempoPermiso, unidadTiempoPermiso);
    }

    // Este método se ejecuta siempre que un Colaborador quiera solicitar la Apertura de una Heladera (generalmente para una Donación de Vianda, un retiro de lote para una Distribución de Viandas o un depósito de lote para una Distribución de Viandas)
    @Override
    public SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, MotivoSolicitud motivo) {
        heladeraInvolucrada.getGestorDeAperturas().revisarMotivoApertura(motivo);

        SolicitudAperturaColaborador solicitudApertura = new SolicitudAperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, this.getTitular(), motivo);
        solicitudApertura.darDeAlta();

        // Agrego el permiso correspondiente para abrir la Heladera involucrada
        PermisoApertura permiso = new PermisoApertura(heladeraInvolucrada, LocalDateTime.now(), true);
        agregarPermiso(permiso);

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.solicitarApertura_info", heladeraInvolucrada.getNombre(), titular.getPersona().getNombre(2)));

        programarRevocacionPermisos(permiso);

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

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.intentarApertura_info", heladeraInvolucrada.getNombre(), titular.getPersona().getNombre(2)));

        return apertura;
    }
}
