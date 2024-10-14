package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.UsoTarjeta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.TarjetaPersonaEnSituacionVulnerableRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.GestorDeAperturas;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class TarjetaPersonaEnSituacionVulnerableService {

    private final TarjetaPersonaEnSituacionVulnerableRepository tarjetaPersonaEnSituacionVulnerableRepository;
    private final PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;
    private final HeladeraService heladeraService;
    private final AccionHeladeraService accionHeladeraService;
    private final GestorDeAperturas gestorDeAperturas;
    private final TarjetaPersonaEnSituacionVulnerableCreator tarjetaPersonaEnSituacionVulnerableCreator;

    public TarjetaPersonaEnSituacionVulnerableService(TarjetaPersonaEnSituacionVulnerableRepository vTarjetaEnSituacionVulnerableRepository, PersonaEnSituacionVulnerableService vPersonaEnSituacionVulnerableService, HeladeraService vHeladeraService, AccionHeladeraService vAccionHeladeraService, GestorDeAperturas vGestorDeAperturas, TarjetaPersonaEnSituacionVulnerableCreator vTarjetaEnSituacionVulnerableCreator){
        tarjetaPersonaEnSituacionVulnerableRepository = vTarjetaEnSituacionVulnerableRepository;
        personaEnSituacionVulnerableService = vPersonaEnSituacionVulnerableService;
        heladeraService = vHeladeraService;
        accionHeladeraService = vAccionHeladeraService;
        gestorDeAperturas = vGestorDeAperturas;
        tarjetaPersonaEnSituacionVulnerableCreator = vTarjetaEnSituacionVulnerableCreator;
    }

    public TarjetaPersonaEnSituacionVulnerable obtenerTarjetaPersonaEnSituacionvulnerable(String tarjetaId) {
        return tarjetaPersonaEnSituacionVulnerableRepository.findById(tarjetaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<TarjetaPersonaEnSituacionVulnerable> obtenerTarjetasPersonaEnSituacionVulnerable() {
        return new ArrayList<>(tarjetaPersonaEnSituacionVulnerableRepository.findAll());
    }

    public TarjetaPersonaEnSituacionVulnerable guardarTarjeta(TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable) {
        return tarjetaPersonaEnSituacionVulnerableRepository.save(tarjetaPersonaEnSituacionVulnerable);
    }

    public TarjetaPersonaEnSituacionVulnerable crearTarjetaPersonaEnSituacionVulnerable(Long personaEnSituacionVulnerableId) {
        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = personaEnSituacionVulnerableService.obtenerPersonaEnSituacionVulnerable(personaEnSituacionVulnerableId);
        return (TarjetaPersonaEnSituacionVulnerable) tarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta(personaEnSituacionVulnerable);
    }

    public Boolean puedeUsar(String tarjetaId) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = obtenerTarjetaPersonaEnSituacionvulnerable(tarjetaId);

        return tarjetaPersonaEnSituacionVulnerable.cantidadUsos() < 4 + 2 * tarjetaPersonaEnSituacionVulnerable.getTitular().getMenoresACargo();
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetearUsos() {
        ArrayList<TarjetaPersonaEnSituacionVulnerable> tarjetasPersonaEnSituacionVulnerable= obtenerTarjetasPersonaEnSituacionVulnerable();

        for (TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable : tarjetasPersonaEnSituacionVulnerable) {
            tarjetaPersonaEnSituacionVulnerable.resetUsos();
            log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.programarRevocacionPermisos_info", tarjetaPersonaEnSituacionVulnerable.getTitular().getPersona().getNombre(2)));
        }
    }

    // Este método se ejecuta siempre que una Persona en Situación Vulnerable quiera realizar la Apertura de una Heladera (generalmente para retirar una Vianda
    public AperturaPersonaEnSituacionVulnerable intentarApertura(String tarjetaId, Long heladeraId) {
        TarjetaPersonaEnSituacionVulnerable tarjeta = obtenerTarjetaPersonaEnSituacionvulnerable(tarjetaId);
        Heladera heladeraInvolucrada = heladeraService.obtenerHeladera(heladeraId);

        // Primero chequeo internamente que pueda realizar la Apertura
        gestorDeAperturas.revisarPermisoAperturaP(heladeraId, tarjeta.getTitular().getId());

        LocalDateTime ahora = LocalDateTime.now();   // Guardo el valor en una variable para usar exactamente el mismo en las líneas de código posteriores

        AperturaPersonaEnSituacionVulnerable apertura = new AperturaPersonaEnSituacionVulnerable(ahora, heladeraInvolucrada, tarjeta.getTitular());
        accionHeladeraService.guardarAccionHeladera(apertura);

        // Registro el Uso de la Tarjeta en la Heladera correspondiente
        UsoTarjeta uso = new UsoTarjeta(ahora, heladeraInvolucrada);
        tarjeta.agregarUso(uso);
        guardarTarjeta(tarjeta);

        log.log(Level.INFO, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.intentarApertura_info", heladeraInvolucrada.getNombre(), tarjeta.getTitular().getPersona().getNombre(2)));

        return apertura;
    }
}
