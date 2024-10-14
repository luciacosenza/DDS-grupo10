package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.RegistroDePersonaEnSituacionVulnerableRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaPersonaEnSituacionVulnerableService;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class RegistroDePersonaEnSituacionVulnerableService extends ContribucionService {

    private final RegistroDePersonaEnSituacionVulnerableRepository registroDePersonaEnSituacionVulnerableRepository;
    private final ColaboradorService colaboradorService;
    private final TarjetaPersonaEnSituacionVulnerableService tarjetaPersonaEnSituacionVulnerableService;
    private final Double multiplicadorPuntos = 2d;

    public RegistroDePersonaEnSituacionVulnerableService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, RegistroDePersonaEnSituacionVulnerableRepository vRegistroDePersonaEnSituacionVulnerableRepository, TarjetaPersonaEnSituacionVulnerableService vTarjetaPersonaEnSituacionVulnerableService) {
        super(vContribucionRepository);
        registroDePersonaEnSituacionVulnerableRepository = vRegistroDePersonaEnSituacionVulnerableRepository;
        colaboradorService = vColaboradorService;
        tarjetaPersonaEnSituacionVulnerableService = vTarjetaPersonaEnSituacionVulnerableService;
    }

    public RegistroDePersonaEnSituacionVulnerable obtenerRegistroDePersonaEnSituacionVulnerable(Long registroId) {
        return registroDePersonaEnSituacionVulnerableRepository.findById(registroId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<RegistroDePersonaEnSituacionVulnerable> obtenerRegistrosDePersonaEnSituacionVulnerableQueSumanPuntos() {
        return new ArrayList<>(registroDePersonaEnSituacionVulnerableRepository.findByYaSumoPuntosFalse());
    }

    public RegistroDePersonaEnSituacionVulnerable guardarRegistroDePersonaEnSituacionVulnerable(RegistroDePersonaEnSituacionVulnerable registro) {
        return registroDePersonaEnSituacionVulnerableRepository.save(registro);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        if (colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);
        log.log(Level.INFO, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    protected void calcularPuntos() {
        ArrayList<RegistroDePersonaEnSituacionVulnerable> registrosDePersonaEnSituacionVulnerable = obtenerRegistrosDePersonaEnSituacionVulnerableQueSumanPuntos();

        for (RegistroDePersonaEnSituacionVulnerable registroDePersonaEnSituacionVulnerable : registrosDePersonaEnSituacionVulnerable) {
            ColaboradorHumano colaborador = (ColaboradorHumano) registroDePersonaEnSituacionVulnerable.getColaborador();

            colaborador.sumarPuntos(multiplicadorPuntos);
            colaboradorService.guardarColaborador(colaborador);

            registroDePersonaEnSituacionVulnerable.setYaSumoPuntos(true);
            guardarRegistroDePersonaEnSituacionVulnerable(registroDePersonaEnSituacionVulnerable);
            confirmarSumaPuntos(registroDePersonaEnSituacionVulnerable.getId(), colaborador.getId(), multiplicadorPuntos);
        }
    }
}
