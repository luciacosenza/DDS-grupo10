package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.broker.Mensaje;
import com.tp_anual_dds.broker.MensajeApertura;
import com.tp_anual_dds.broker.MensajeSolicitudApertura;
import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.estado_de_solicitud.EstadoExpirada;
import com.tp_anual_dds.domain.estado_de_solicitud.EstadoPosible;
import com.tp_anual_dds.domain.estado_de_solicitud.EstadoRealizada;
import com.tp_anual_dds.domain.estado_de_solicitud.EstadoSolicitud;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoApertura;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoAperturaActivo;
import com.tp_anual_dds.sistema.Sistema;

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
    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
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
        return true; // Se puede definir alguna lógica específica si es necesario
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

        // Programa la tarea para que se ejecute una vez después de 3 horas
        timer.schedule(revocacionPermisos, delay, unidad);
    }

    // Este método se ejecuta siempre que un Colaborador quiera solicitar la Apertura de una Heladera (generalmente para una Donación de Vianda, un retiro de lote para una Distribución de Viandas o un depósito de lote para una Distribución de Viandas)
    // TODO La lógica de este método puede cambiar al implementar el Broker
    @Override
    public SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, MotivoSolicitud motivo) {
        estadoSolicitud.manejar(this);
        // A partir de acá, el Estado de Solicitud pasará a Pendiente, hasta que se de de alta la Solicitud de Apertura
        
        MensajeSolicitudApertura mensajeSolicitudApertura = new MensajeSolicitudApertura(heladeraInvolucrada, motivo);
        
        // Envío al Broker el Mensaje de Solicitud de Apertura
        new Thread( () -> {
            try {
                Sistema.getBroker().agregarMensaje(mensajeSolicitudApertura);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("El hilo fue interrumpido: " + e.getMessage()); // TODO Chequear si está bien que lo tire en System.err
            }
        }).start();

        // Registramos la Solicitud de Apertura en el Sistema
        SolicitudAperturaColaborador solicitudApertura = new SolicitudAperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, this.getTitular(), motivo);
        solicitudApertura.darDeAlta();

        setEstadoSolicitud(new EstadoRealizada());
        // A partir de acá, el Estado de Solicitud pasará a Realizada, hasta que se revoquen los permisos, pasadas las 3 horas

        // Actualizo (añado) los permisos correspondientes a la Heladera involucrada
        permiso.setHeladeraPermitida(heladeraInvolucrada);
        permiso.actualizarFechaOtorgamiento();

        programarRevocacionPermisos();

        return solicitudApertura;
    }

    // Este método se ejecuta siempre que un Colaborador quiera realizar la Apertura de una Heladera (generalmente para una Donación de Vianda, un retiro de lote para una Distribución de Viandas o un depósito de lote para una Distribución de Viandas)
    @Override
    public AperturaColaborador intentarApertura(HeladeraActiva heladeraInvolucrada) throws InterruptedException {
        MensajeApertura mensajeApertura = new MensajeApertura(heladeraInvolucrada, getTitular());
        
        // Envío al Broker el Mensaje de Apertura
        new Thread( () -> {
            try {
                Sistema.getBroker().agregarMensaje(mensajeApertura);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("El hilo fue interrumpido: " + e.getMessage()); // TODO Chequear si está bien que lo tire en System.err
            }
        }).start();

        // Registramos la Apertura en el Sistema
        AperturaColaborador apertura = new AperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, this.getTitular());
        apertura.darDeAlta();

        // Reseteo el Estado de Solicitud
        setEstadoSolicitud(new EstadoPosible());
        // A partir de acá, el Estado de Solicitud pasará a Posible, hasta que se solicite una nueva Apertura

        return apertura;
    }
}
