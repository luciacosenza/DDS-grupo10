package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;

import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.TarjetaColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.GestorDeAperturas;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public TarjetaColaboradorService(TarjetaColaboradorRepository vTarjetaColaboradorRepository, ColaboradorService vColaboradorService, PermisoAperturaService vPermisoAperturaService, HeladeraService vHeladeraService, AccionHeladeraService vAccionHeladeraService, GestorDeAperturas vGestorDeAperturas, TarjetaColaboradorCreator vTarjetaColaboradorCreator) {
        tarjetaColaboradorRepository = vTarjetaColaboradorRepository;
        colaboradorService = vColaboradorService;
        permisoAperturaService = vPermisoAperturaService;
        heladeraService = vHeladeraService;
        accionHeladeraService = vAccionHeladeraService;
        gestorDeAperturas = vGestorDeAperturas;
        tarjetaColaboradorCreator = vTarjetaColaboradorCreator;
    }

    public TarjetaColaborador obtenerTarjetaColaborador(String tarjetaId) {
        return tarjetaColaboradorRepository.findById(tarjetaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public TarjetaColaborador guardarTarjetaColaborador(TarjetaColaborador TarjetaColaborador){
        return tarjetaColaboradorRepository.save(TarjetaColaborador);
    }

    public TarjetaColaborador crearTarjetaColaborador(Long colaboradorId){
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        return (TarjetaColaborador) tarjetaColaboradorCreator.crearTarjeta(colaborador);
    }

    public SolicitudAperturaColaborador solicitarApertura(String tarjetaId, Long heladeraId, SolicitudAperturaColaborador.MotivoSolicitud motivo) {
        TarjetaColaborador tarjetaColaborador = obtenerTarjetaColaborador(tarjetaId);
        Heladera heladeraInvolucrada = heladeraService.obtenerHeladera(heladeraId);

        gestorDeAperturas.revisarMotivoApertura(heladeraId, motivo);

        SolicitudAperturaColaborador solicitudApertura = new SolicitudAperturaColaborador(LocalDateTime.now(), heladeraInvolucrada, tarjetaColaborador.getTitular(), motivo);
        accionHeladeraService.guardarAccionHeladera(solicitudApertura);

        // Agrego el permiso correspondiente para abrir la Heladera involucrada
        PermisoApertura permisoApertura = new PermisoApertura(heladeraInvolucrada, LocalDateTime.now(), true);
        tarjetaColaborador.agregarPermiso(permisoApertura);
        guardarTarjetaColaborador(tarjetaColaborador);

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaborador.solicitarApertura_info", heladeraInvolucrada.getNombre(), tarjetaColaborador.getTitular().getPersona().getNombre(2)));

        return solicitudApertura;
    }

    public AperturaColaborador intentarApertura(String tarjetaId, Long heladeraId) {
        // Primero chequeo internamente que pueda realizar la Apertura
        TarjetaColaborador tarjetaColaborador = obtenerTarjetaColaborador(tarjetaId);
        Heladera heladera = heladeraService.obtenerHeladera(heladeraId);
        ColaboradorHumano titular = tarjetaColaborador.getTitular();

        gestorDeAperturas.revisarPermisoAperturaC(heladera.getId(), titular.getId());

        AperturaColaborador apertura = new AperturaColaborador(LocalDateTime.now(), heladera, titular);
        accionHeladeraService.guardarAccionHeladera(apertura);

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaColaborador.intentarApertura_info", heladera.getNombre(), titular.getPersona().getNombre(2)));

        return apertura;
    }
}