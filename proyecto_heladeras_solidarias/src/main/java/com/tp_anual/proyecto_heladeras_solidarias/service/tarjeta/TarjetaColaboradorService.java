package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorActiva;

import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.TarjetaColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.GestorDeAperturas;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Log
@Service
public class TarjetaColaboradorService {

    private final TarjetaColaboradorRepository tarjetaColaboradorRepository;
    private final ColaboradorService colaboradorService;
    private final PermisoAperturaService permisoAperturaService;
    private final HeladeraService heladeraService;
    private final GestorDeAperturas gestorDeAperturas;
    private final AccionHeladeraService accionHeladeraService;
    private final TarjetaColaboradorCreator tarjetaColaboradorCreator;
    private final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    public TarjetaColaboradorService(TarjetaColaboradorRepository vTarjetaColaboradorRepository, ColaboradorService vColaboradorService, PermisoAperturaService vPermisoAperturaService, HeladeraService vHeladeraService, AccionHeladeraService vAccionHeladeraService, GestorDeAperturas vGestorDeAperturas, TarjetaColaboradorCreator vTarjetaColaboradorCreator){
        tarjetaColaboradorRepository = vTarjetaColaboradorRepository;
        colaboradorService = vColaboradorService;
        permisoAperturaService = vPermisoAperturaService;
        heladeraService = vHeladeraService;
        accionHeladeraService = vAccionHeladeraService;
        gestorDeAperturas = vGestorDeAperturas;
        tarjetaColaboradorCreator = vTarjetaColaboradorCreator
    }

    public TarjetaColaboradorActiva obtenerTarjetaColaborador(String tarjetaId) {
        return (TarjetaColaboradorActiva) tarjetaColaboradorRepository.findById(tarjetaId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public TarjetaColaboradorActiva guardarTarjetaColaborador(TarjetaColaboradorActiva tarjetaColaboradorActiva){
        return tarjetaColaboradorRepository.save(tarjetaColaboradorActiva);
    }

    public TarjetaColaboradorActiva crearTarjetaColaborador(Long colaboradorId){
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        return (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaborador);
    }

    public void programarRevocacionPermisos(Long permisoAperturaId, String tarjetaId, Long heladeraId) {
        TarjetaColaboradorActiva tarjetaColaborador = obtenerTarjetaColaborador(tarjetaId);
        PermisoApertura permisoApertura = permisoAperturaService.obtenerPermisoApertura(permisoAperturaId);

        HeladeraActiva heladeraInvolucrada = permisoApertura.getHeladeraPermitida();
        Integer tiempoPermiso = heladeraInvolucrada.getTiempoPermiso();
        TimeUnit unidadTiempoPermiso = heladeraInvolucrada.getUnidadTiempoPermiso();

        Runnable revocacionPermisos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime fechaOtorgamiento = permisoApertura.getFechaOtorgamiento();
            long horasPasadas = ChronoUnit.HOURS.between(fechaOtorgamiento, ahora);
            if (horasPasadas >= 3) {
                permisoApertura.setOtorgado(false);
                permisoAperturaService.guardarPermisoApertura(permisoApertura);
            }

            log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.programarRevocacionPermisos_info", permisoApertura.getHeladeraPermitida().getNombre(), tarjetaColaborador.getTitular().getPersona().getNombre(2)));
        };

        // Programa la tarea para que se ejecute una vez después de 3 horas
        timer.schedule(revocacionPermisos, tiempoPermiso, unidadTiempoPermiso);
    }

    public SolicitudAperturaColaborador solicitarApertura(String tarjetaId, Long heladeraId, SolicitudAperturaColaborador.MotivoSolicitud motivo) {
        TarjetaColaboradorActiva tarjetaColaborador = obtenerTarjetaColaborador(tarjetaId);
        HeladeraActiva heladeraInvolucrada = heladeraService.obtenerHeladera(heladeraId);

        gestorDeAperturas.revisarMotivoApertura(heladeraId, motivo);

        SolicitudAperturaColaborador solicitudApertura = new SolicitudAperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, tarjetaColaborador.getTitular(), motivo);
        solicitudApertura.darDeAlta();
        accionHeladeraService.guardarAccionHeladera(solicitudApertura);

        // Agrego el permiso correspondiente para abrir la Heladera involucrada
        PermisoApertura permisoApertura = new PermisoApertura(heladeraInvolucrada, LocalDateTime.now(), true);
        tarjetaColaborador.agregarPermiso(permisoApertura);
        guardarTarjetaColaborador(tarjetaColaborador);

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.solicitarApertura_info", heladeraInvolucrada.getNombre(), tarjetaColaborador.getTitular().getPersona().getNombre(2)));

        programarRevocacionPermisos(permisoApertura.getId(), tarjetaId, heladeraId);

        return solicitudApertura;
    }

    public AperturaColaborador intentarApertura(String tarjetaId, Long heladeraId) {
        // Primero chequeo internamente que pueda realizar la Apertura
        TarjetaColaboradorActiva tarjetaColaborador = obtenerTarjetaColaborador(tarjetaId);
        HeladeraActiva heladera = heladeraService.obtenerHeladera(heladeraId);
        ColaboradorHumano titular = tarjetaColaborador.getTitular();

        gestorDeAperturas.revisarPermisoAperturaC(heladera.getId(), titular.getId());

        // Registramos la Apertura en el Sistema
        AperturaColaborador apertura = new AperturaColaborador(LocalDateTime.now(), heladera, titular);
        apertura.darDeAlta();
        accionHeladeraService.guardarAccionHeladera(apertura);

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaboradorActiva.intentarApertura_info", heladera.getNombre(), titular.getPersona().getNombre(2)));

        return apertura;
    }
}
