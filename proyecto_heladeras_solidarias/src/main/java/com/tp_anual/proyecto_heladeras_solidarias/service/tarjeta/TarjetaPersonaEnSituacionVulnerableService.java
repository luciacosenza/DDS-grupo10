package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.Tarjeta;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.UsoTarjeta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.TarjetaPersonaEnSituacionVulnerableRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.GestorDeAperturas;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
@Log
public class TarjetaPersonaEnSituacionVulnerableService extends TarjetaService {

    private final TarjetaPersonaEnSituacionVulnerableRepository tarjetaPersonaEnSituacionVulnerableRepository;
    private final PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;
    private final AccionHeladeraService accionHeladeraService;
    private final GestorDeAperturas gestorDeAperturas;
    private final TarjetaPersonaEnSituacionVulnerableCreator tarjetaPersonaEnSituacionVulnerableCreator;

    public TarjetaPersonaEnSituacionVulnerableService(TarjetaPersonaEnSituacionVulnerableRepository vTarjetaEnSituacionVulnerableRepository, PersonaEnSituacionVulnerableService vPersonaEnSituacionVulnerableService, AccionHeladeraService vAccionHeladeraService, GestorDeAperturas vGestorDeAperturas, TarjetaPersonaEnSituacionVulnerableCreator vTarjetaEnSituacionVulnerableCreator){
        super();
        tarjetaPersonaEnSituacionVulnerableRepository = vTarjetaEnSituacionVulnerableRepository;
        personaEnSituacionVulnerableService = vPersonaEnSituacionVulnerableService;
        accionHeladeraService = vAccionHeladeraService;
        gestorDeAperturas = vGestorDeAperturas;
        tarjetaPersonaEnSituacionVulnerableCreator = vTarjetaEnSituacionVulnerableCreator;
    }

    @Override
    public TarjetaPersonaEnSituacionVulnerable obtenerTarjeta(String tarjetaPersonaEnSituacionVulnerableId) {
        return tarjetaPersonaEnSituacionVulnerableRepository.findById(tarjetaPersonaEnSituacionVulnerableId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public List<TarjetaPersonaEnSituacionVulnerable> obtenerTarjetas() {
        return new ArrayList<>(tarjetaPersonaEnSituacionVulnerableRepository.findAll());
    }

    @Override
    public TarjetaPersonaEnSituacionVulnerable guardarTarjeta(Tarjeta tarjetaPersonaEnSituacionVulnerable) {
        return tarjetaPersonaEnSituacionVulnerableRepository.saveAndFlush((TarjetaPersonaEnSituacionVulnerable) tarjetaPersonaEnSituacionVulnerable);
    }

    @Override
    public TarjetaPersonaEnSituacionVulnerable crearTarjeta(Long personaEnSituacionVulnerableId) {
        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = personaEnSituacionVulnerableService.obtenerPersonaEnSituacionVulnerable(personaEnSituacionVulnerableId);
        return (TarjetaPersonaEnSituacionVulnerable) tarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta(personaEnSituacionVulnerable);
    }

    public void agregarUso(String tarjetaPersonaEnSituacionVulnerableId, UsoTarjeta uso) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = obtenerTarjeta(tarjetaPersonaEnSituacionVulnerableId);
        tarjetaPersonaEnSituacionVulnerable.agregarUso(uso);
    }

    public void resetUsos(String tarjetaPersonaEnSituacionVulnerableId) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = obtenerTarjeta(tarjetaPersonaEnSituacionVulnerableId);
        tarjetaPersonaEnSituacionVulnerable.resetUsos();
    }

    @Override
    public Boolean puedeUsar(String tarjetaPersonaEnSituacionVulnerableId) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = obtenerTarjeta(tarjetaPersonaEnSituacionVulnerableId);

        return tarjetaPersonaEnSituacionVulnerable.cantidadUsos() < 4 + 2 * tarjetaPersonaEnSituacionVulnerable.getTitular().getMenoresACargo();
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetearUsos() {
        List<TarjetaPersonaEnSituacionVulnerable> tarjetasPersonaEnSituacionVulnerable= obtenerTarjetas();

        for (TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable : tarjetasPersonaEnSituacionVulnerable) {
            resetUsos(tarjetaPersonaEnSituacionVulnerable.getCodigo());
            log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.programarRevocacionPermisos_info", tarjetaPersonaEnSituacionVulnerable.getTitular().getPersona().getNombre(2)));
        }
    }

    // Este método se ejecuta siempre que una Persona en Situación Vulnerable quiera realizar la Apertura de una Heladera (generalmente para retirar una Vianda
    public AperturaPersonaEnSituacionVulnerable intentarApertura(String tarjetaPersonaEnSituacionVulnerableId, Heladera heladeraInvolucrada) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = obtenerTarjeta(tarjetaPersonaEnSituacionVulnerableId);

        if (!puedeUsar(tarjetaPersonaEnSituacionVulnerableId)) {
            log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.puedeUsar_err", tarjetaPersonaEnSituacionVulnerable.getTitular().getPersona().getNombre(2)));
            throw new UnsupportedOperationException(I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.puedeUsar_exception"));
        }

        // Primero chequeo internamente que pueda realizar la Apertura
        gestorDeAperturas.revisarPermisoAperturaP(heladeraInvolucrada);

        LocalDateTime ahora = LocalDateTime.now();   // Guardo el valor en una variable para usar exactamente el mismo en las líneas de código posteriores

        AperturaPersonaEnSituacionVulnerable apertura = new AperturaPersonaEnSituacionVulnerable(ahora, heladeraInvolucrada, tarjetaPersonaEnSituacionVulnerable.getTitular());
        accionHeladeraService.guardarAccionHeladera(apertura);

        // Registro el Uso de la Tarjeta en la Heladera correspondiente
        UsoTarjeta uso = new UsoTarjeta(ahora, heladeraInvolucrada);
        agregarUso(tarjetaPersonaEnSituacionVulnerable.getCodigo(), uso);   // Al guardar la tarjeta, se guarda el uso por cascada

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.intentarApertura_info", heladeraInvolucrada.getNombre(), tarjetaPersonaEnSituacionVulnerable.getTitular().getPersona().getNombre(2)));

        return apertura;
    }
}
