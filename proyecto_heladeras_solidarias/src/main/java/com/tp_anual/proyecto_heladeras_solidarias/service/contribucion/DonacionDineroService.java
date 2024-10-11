package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.DonacionDineroRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

@Service
@Log
public class DonacionDineroService extends ContribucionService {

    private final DonacionDineroRepository donacionDineroRepository;
    private final Double multiplicadorPuntos = 0.5;

    public DonacionDineroService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, DonacionDineroRepository vDonacionDineroRepository) {
        super(vContribucionRepository, vColaboradorService);
        donacionDineroRepository = vDonacionDineroRepository;
    }

    public DonacionDinero obtenerDonacionDinero(Long donacionDineroId) {
        return donacionDineroRepository.findById(donacionDineroId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public ArrayList<DonacionDinero> obtenerDonacionesDineroQueSumanPuntos() {
        return new ArrayList<>(donacionDineroRepository.findDonacionesDineroParaCalcular());
    }

    public DonacionDinero guardarDonacionDinero(DonacionDinero donacionDinero) {
        return donacionDineroRepository.save(donacionDinero);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    public void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        Colaborador colaborador = colaboradorService.obtenerColaborador(colaboradorId);
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

            colaborador.sumarPuntos(puntosASumar);
            colaboradorService.guardarColaborador(colaborador);

            donacionDinero.setYaSumoPuntos(true);
            guardarDonacionDinero(donacionDinero);
            confirmarSumaPuntos(donacionDinero.getId(), colaborador.getId(), puntosASumar);
        }
    }
}