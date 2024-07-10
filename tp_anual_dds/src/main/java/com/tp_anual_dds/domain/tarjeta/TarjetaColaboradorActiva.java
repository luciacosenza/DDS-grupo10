package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;

public class TarjetaColaboradorActiva extends TarjetaColaborador {
    public enum MotivoSolicitud {
        INGRESAR_DONACION,
        INGRESAR_LOTE_DE_DISTRIBUCION,
        RETIRAR_LOTE_DE_DISTRIBUCION
    }
    
    public TarjetaColaboradorActiva(String vCodigo, ColaboradorHumano vTitular) {
        codigo = vCodigo;
        usos = new ArrayList<>();
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
    public void revocarPermisos() {
        Runnable revocacionPermisos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime fechaOtorgamiento = permiso.getFechaOtorgamiento();
            long tiempoPasado = ChronoUnit.HOURS.between(fechaOtorgamiento, ahora);
            if (tiempoPasado >= 3) {
                permiso.resetHeladerasPermitidas();
                setEstadoSolicitud(new EstadoExpirada());
            }
        };

        // Programa la tarea para que se ejecute una vez después de 3 horas
        timer.schedule(() -> revocacionPermisos, 3, TimeUnit.HOURS);
    }

    @Override
    public void solicitarApertura(MotivoSolicitud motivo, ArrayList<Heladera> heladerasInvolucradas) {
        estadoSolicitud.manejar(this);
        
        System.out.println(String.format("Solicitud de Apertura - Motivo: %s", motivo));
        // Esto es temporal, para que no tire errores. La logica es *registrar la solicitud en el sistema*

        for(Heladera heladera : heladerasInvolucradas) {
            permiso.agregarHeladeraPermitida(heladera);
        }
        permiso.actualizarFechaOtorgamiento();

        revocarPermisos();

        setEstadoSolicitud(new EstadoRealizada());
    }
}
