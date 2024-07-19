package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoExpirada;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoPosible;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoRealizada;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoSolicitud;
import com.tp_anual_dds.domain.heladera.AccionHeladera;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.AccionHeladera.TipoAccion;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoAperturaActivo;

public class TarjetaColaboradorActiva extends TarjetaColaborador {
    public enum MotivoSolicitud {
        INGRESAR_DONACION,
        INGRESAR_LOTE_DE_DISTRIBUCION,
        RETIRAR_LOTE_DE_DISTRIBUCION
    }
    
    public TarjetaColaboradorActiva(String vCodigo, ColaboradorHumano vTitular) {
        codigo = vCodigo;
        titular = vTitular;
        estadoSolicitud = new EstadoPosible();
        permiso = new PermisoAperturaActivo();
    }

    @Override
    public ColaboradorHumano getTitular() {
        return titular;
    }

    @Override
    public void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud) {
        estadoSolicitud = vEstadoSolicitud;
    }

    @Override
    public Boolean puedeUsar() {
        return true; // Se puede definir alguna lógica especifica si es necesario.
    }

    @Override
    public void programarRevocacionPermisos() {
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
        timer.schedule(() -> revocacionPermisos, 3, TimeUnit.HOURS);
    }

    @Override
    public void solicitarApertura(MotivoSolicitud motivo, HeladeraActiva heladeraInvolucrada) {
        estadoSolicitud.manejar(this);
        
        AccionHeladera solicitudApertura = new AccionHeladera(TipoAccion.SOLICITUD_APERTURA, LocalDateTime.now(), heladeraInvolucrada, this.getTitular());
        solicitudApertura.darDeAlta();

        setEstadoSolicitud(new EstadoRealizada());

        permiso.setHeladeraPermitida(heladeraInvolucrada);
        permiso.actualizarFechaOtorgamiento();

        programarRevocacionPermisos();
    }

    @Override
    public void intentarApertura(HeladeraActiva heladeraInvolucrada) {
        if(!(estadoSolicitud instanceof EstadoRealizada) || !(permiso.esHeladeraPermitida(heladeraInvolucrada))) {
            throw new UnsupportedOperationException("No cuenta con los permisos para abrir esta heladera");
        }

        AccionHeladera apertura = new AccionHeladera(TipoAccion.APERTURA, LocalDateTime.now(), heladeraInvolucrada, this.getTitular());
        apertura.darDeAlta();

        setEstadoSolicitud(new EstadoPosible());
    }
}
