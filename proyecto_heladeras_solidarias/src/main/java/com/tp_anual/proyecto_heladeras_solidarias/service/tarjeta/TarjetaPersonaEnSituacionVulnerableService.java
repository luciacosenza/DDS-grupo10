package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.UsoTarjeta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.HeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.TarjetaPersonaEnSituacionVulnerableRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.GestorDeAperturas;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;


import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Service
@Log
public class TarjetaPersonaEnSituacionVulnerableService {

    private final TarjetaPersonaEnSituacionVulnerableRepository tarjetaPersonaEnSituacionVulnerableRepository;
    private final HeladeraService heladeraService;
    private final AccionHeladeraService accionHeladeraService;
    private final GestorDeAperturas gestorDeAperturas;
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    public TarjetaPersonaEnSituacionVulnerableService(TarjetaPersonaEnSituacionVulnerableRepository vTarjetaEnSituacionVulnerableRepository, HeladeraService vHeladeraService, AccionHeladeraService vAccionHeladeraService,GestorDeAperturas vGestorDeAperturas){
        tarjetaPersonaEnSituacionVulnerableRepository = vTarjetaEnSituacionVulnerableRepository;
        heladeraService = vHeladeraService;
        accionHeladeraService = vAccionHeladeraService;
        gestorDeAperturas = vGestorDeAperturas;
    }

    public TarjetaPersonaEnSituacionVulnerable obtenerTarjeta(String tarjetaId){
        return tarjetaPersonaEnSituacionVulnerableRepository.findById(tarjetaId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public TarjetaPersonaEnSituacionVulnerable guardarTarjeta(TarjetaPersonaEnSituacionVulnerable tarjeta){
        return tarjetaPersonaEnSituacionVulnerableRepository.save(tarjeta);
    }

    public Boolean puedeUsar(String tarjetaId) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = obtenerTarjeta(tarjetaId);

        return tarjetaPersonaEnSituacionVulnerable.cantidadUsos() < 4 + 2 * tarjetaPersonaEnSituacionVulnerable.getTitular().getMenoresACargo();
    }

    public void programarReseteoUsos(String tarjetaId) {
        TarjetaPersonaEnSituacionVulnerable tarjeta = obtenerTarjeta(tarjetaId);

        Integer periodo = 1;
        TimeUnit unidad = TimeUnit.DAYS;

        Runnable reseteoUsos = () -> {
            tarjeta.resetUsos();
            log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.programarRevocacionPermisos_info", tarjeta.getTitular().getPersona().getNombre(2)));
        };

        guardarTarjeta(tarjeta);

        // Programa la tarea para que se ejecute una vez por día
        timer.scheduleAtFixedRate(reseteoUsos, 0, periodo, unidad);
    }

    // Este método se ejecuta siempre que una Persona en Situación Vulnerable quiera realizar la Apertura de una Heladera (generalmente para retirar una Vianda
    public AperturaPersonaEnSituacionVulnerable intentarApertura(String tarjetaId, Long heladeraId) {
        TarjetaPersonaEnSituacionVulnerable tarjeta = obtenerTarjeta(tarjetaId);
        HeladeraActiva heladeraInvolucrada = heladeraService.obtenerHeladera(heladeraId);

        // Primero chequeo internamente que pueda realizar la Apertura
        gestorDeAperturas.revisarPermisoAperturaP(heladeraId, tarjeta.getTitular().getId());

        LocalDateTime ahora = LocalDateTime.now();   // Guardo el valor en una variable para usar exactamente el mismo en las líneas de código posteriores

        AperturaPersonaEnSituacionVulnerable apertura = new AperturaPersonaEnSituacionVulnerable(ahora, heladeraInvolucrada, tarjeta.getTitular());
        apertura.darDeAlta();
        accionHeladeraService.guardarAccionHeladera(apertura);

        // Registro el Uso de la Tarjeta en la Heladera correspondiente
        UsoTarjeta uso = new UsoTarjeta(ahora, heladeraInvolucrada);
        tarjeta.agregarUso(uso);
        guardarTarjeta(tarjeta);

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.intentarApertura_info", heladeraInvolucrada.getNombre(), tarjeta.getTitular().getPersona().getNombre(2)));

        return apertura;
    }
}
