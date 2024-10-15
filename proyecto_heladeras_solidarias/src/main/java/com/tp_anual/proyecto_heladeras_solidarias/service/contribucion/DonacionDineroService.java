package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.DonacionDineroRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorPuntosService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class DonacionDineroService extends ContribucionService {

    private final DonacionDineroRepository donacionDineroRepository;
    private final ColaboradorPuntosService colaboradorPuntosService;
    private final Double multiplicadorPuntos = 0.5;

    public DonacionDineroService(DonacionDineroRepository vDonacionDineroRepository, ColaboradorPuntosService vColaboradorPuntosService) {
        super();
        donacionDineroRepository = vDonacionDineroRepository;
        colaboradorPuntosService = vColaboradorPuntosService;
    }

    @Override
    public DonacionDinero obtenerContribucion(Long donacionDineroId) {
        return donacionDineroRepository.findById(donacionDineroId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<DonacionDinero> obtenerDonacionesDineroQueSumanPuntos() {
        return new ArrayList<>(donacionDineroRepository.findDonacionesDineroParaCalcular());
    }

    @Override
    public DonacionDinero guardarContribucion(Contribucion donacionDinero) {
        return donacionDineroRepository.save((DonacionDinero) donacionDinero);
    }

    @Override
    public void validarIdentidad(Long donacionDineroId, Colaborador colaborador) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    public void confirmarSumaPuntos(Long contribucionId, Colaborador colaborador, Double puntosSumados) {
        log.log(Level.INFO, I18n.getMessage("contribucion.DonacionDinero.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void calcularPuntos() {
        ArrayList<DonacionDinero> donacionesDinero = obtenerDonacionesDineroQueSumanPuntos();

        for (DonacionDinero donacionDinero : donacionesDinero) {
            Double puntosASumar = donacionDinero.getMonto() * multiplicadorPuntos;
            Colaborador colaborador = donacionDinero.getColaborador();
            colaboradorPuntosService.sumarPuntos(colaborador.getId(), puntosASumar);

            donacionDinero.sumoPuntos();
            guardarContribucion(donacionDinero);    // Al guardar la contribución, se guarda el colaborador por cascada (aunque ya había sido guardado)
            confirmarSumaPuntos(donacionDinero.getId(), colaborador, puntosASumar);
        }
    }
}