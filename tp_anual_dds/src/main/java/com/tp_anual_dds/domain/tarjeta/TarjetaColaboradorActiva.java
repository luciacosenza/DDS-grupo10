package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoExpirada;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoPosible;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoRealizada;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoSolicitud;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoApertura;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoAperturaActivo;

public class TarjetaColaboradorActiva extends TarjetaColaborador {    
    public TarjetaColaboradorActiva(ColaboradorHumano vTitular) {
        codigo = GeneradorCodigo.generarCodigo(true);
        titular = vTitular;
        estadoSolicitud = new EstadoPosible();
        permiso = new PermisoAperturaActivo();
    }

    @Override
    public ColaboradorHumano getTitular() {
        return titular;
    }

    @Override
    public PermisoApertura getPermiso() {
        return permiso;
    }

    @Override
    public void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud) {
        estadoSolicitud = vEstadoSolicitud;
    }

    @Override
    public Boolean puedeUsar() {
        return true; // Se puede definir alguna logica especifica si es necesario
    }

    @Override
    public void programarRevocacionPermisos() {
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
        };

        // Programa la tarea para que se ejecute una vez despues de 3 horas
        timer.schedule(revocacionPermisos, delay, unidad);
    }

    @Override
    public SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, SolicitudAperturaColaborador.MotivoSolicitud motivo) {
        estadoSolicitud.manejar(this);
        
        SolicitudAperturaColaborador solicitudApertura = new SolicitudAperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, this.getTitular(), motivo);
        solicitudApertura.darDeAlta();

        setEstadoSolicitud(new EstadoRealizada());

        permiso.setHeladeraPermitida(heladeraInvolucrada);
        permiso.actualizarFechaOtorgamiento();

        programarRevocacionPermisos();

        return solicitudApertura;
    }

    @Override
    public AperturaColaborador intentarApertura(HeladeraActiva heladeraInvolucrada) {
        if(!(estadoSolicitud instanceof EstadoRealizada) || !(permiso.esHeladeraPermitida(heladeraInvolucrada))) {
            throw new UnsupportedOperationException("No cuenta con los permisos para abrir esta heladera");
        }

        AperturaColaborador apertura = new AperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, this.getTitular());
        apertura.darDeAlta();

        setEstadoSolicitud(new EstadoPosible());

        return apertura;
    }
}
