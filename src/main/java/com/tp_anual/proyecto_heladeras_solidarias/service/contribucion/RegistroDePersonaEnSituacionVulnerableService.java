package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion.DomicilioFaltanteRPESVException;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.RegistroDePersonaEnSituacionVulnerableRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorPuntosService;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
@Log
public class RegistroDePersonaEnSituacionVulnerableService extends ContribucionService {

    private final RegistroDePersonaEnSituacionVulnerableRepository registroDePersonaEnSituacionVulnerableRepository;
    private final ColaboradorPuntosService colaboradorPuntosService;
    private final Double multiplicadorPuntos = 2d;

    private final I18nService i18nService;

    public RegistroDePersonaEnSituacionVulnerableService(RegistroDePersonaEnSituacionVulnerableRepository vRegistroDePersonaEnSituacionVulnerableRepository, ColaboradorPuntosService vColaboradorPuntosService, I18nService vI18nService) {
        super();
        registroDePersonaEnSituacionVulnerableRepository = vRegistroDePersonaEnSituacionVulnerableRepository;
        colaboradorPuntosService = vColaboradorPuntosService;

        i18nService = vI18nService;
    }

    @Override
    public RegistroDePersonaEnSituacionVulnerable obtenerContribucion(Long registroDePersonaEnSituacionVulnerableId) {
        return registroDePersonaEnSituacionVulnerableRepository.findById(registroDePersonaEnSituacionVulnerableId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }

    public List<RegistroDePersonaEnSituacionVulnerable> obtenerRegistrosDePersonaEnSituacionVulnerableQueSumanPuntos() {
        return new ArrayList<>(registroDePersonaEnSituacionVulnerableRepository.findByYaSumoPuntosFalse());
    }

    @Override
    public RegistroDePersonaEnSituacionVulnerable guardarContribucion(Contribucion registroDePersonaEnSituacionVulnerable) {
        return registroDePersonaEnSituacionVulnerableRepository.saveAndFlush((RegistroDePersonaEnSituacionVulnerable) registroDePersonaEnSituacionVulnerable);
    }

    @Override
    public void validarIdentidad(Long registroDePersonaEnSituacionVulnerableId, Colaborador colaborador) throws DomicilioFaltanteRPESVException {
        if (colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, i18nService.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new DomicilioFaltanteRPESVException();
        }
    }

    @Override
    protected void confirmarSumaPuntos(Long registroDePersonaEnSituacionVulnerableId, Colaborador colaborador, Double puntosSumados) {
        log.log(Level.INFO, i18nService.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    protected void calcularPuntos() {
        List<RegistroDePersonaEnSituacionVulnerable> registrosDePersonaEnSituacionVulnerable = obtenerRegistrosDePersonaEnSituacionVulnerableQueSumanPuntos();

        for (RegistroDePersonaEnSituacionVulnerable registroDePersonaEnSituacionVulnerable : registrosDePersonaEnSituacionVulnerable) {
            ColaboradorHumano colaborador = (ColaboradorHumano) registroDePersonaEnSituacionVulnerable.getColaborador();
            colaboradorPuntosService.sumarPuntos(colaborador.getId(), multiplicadorPuntos);

            registroDePersonaEnSituacionVulnerable.sumoPuntos();
            guardarContribucion(registroDePersonaEnSituacionVulnerable);    // Al guardar la contribución, se guarda el colaborador por cascada (aunque ya había sido guardado)
            confirmarSumaPuntos(registroDePersonaEnSituacionVulnerable.getId(), colaborador, multiplicadorPuntos);
        }
    }
}
