package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.HacerseCargoDeHeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class HacerseCargoDeHeladeraService extends ContribucionService {

    private final HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository;
    private final ColaboradorService colaboradorService;
    private final Double multiplicadorPuntos = 5d;

    public HacerseCargoDeHeladeraService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, HacerseCargoDeHeladeraRepository vHacerseCargoDeHeladeraRepository) {
        super(vContribucionRepository);
        hacerseCargoDeHeladeraRepository = vHacerseCargoDeHeladeraRepository;
        colaboradorService = vColaboradorService;
    }

    public HacerseCargoDeHeladera obtenerHacerseCargoDeHeladera(Long hacerseCargoId) {
        return hacerseCargoDeHeladeraRepository.findById(hacerseCargoId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<HacerseCargoDeHeladera> obtenerHacerseCargoDeHeladerasQueSumanPuntos() {
        return new ArrayList<>(hacerseCargoDeHeladeraRepository.findHacerseCargoDeHeladeras());
    }

    public HacerseCargoDeHeladera guardarHacerseCargoDeHeladera(HacerseCargoDeHeladera hacerseCargoDeHeladera) {
        return hacerseCargoDeHeladeraRepository.save(hacerseCargoDeHeladera);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        ColaboradorJuridico colaborador = colaboradorService.obtenerColaboradorJuridico(colaboradorId);
        log.log(Level.INFO, I18n.getMessage("contribucion.HacerseCargoDeHeladera.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    protected void calcularPuntos() {
        ArrayList<HacerseCargoDeHeladera> hacerseCargoDeLaHeladeras = obtenerHacerseCargoDeHeladerasQueSumanPuntos();

        for (HacerseCargoDeHeladera hacerseCargoDeHeladera : hacerseCargoDeLaHeladeras ) {
            ColaboradorJuridico colaborador = (ColaboradorJuridico) hacerseCargoDeHeladera.getColaborador();

            colaborador.sumarPuntos(multiplicadorPuntos);
            colaboradorService.guardarColaborador(colaborador);

            hacerseCargoDeHeladera.setYaSumoPuntos(true);
            guardarHacerseCargoDeHeladera(hacerseCargoDeHeladera);
            confirmarSumaPuntos(hacerseCargoDeHeladera.getId(), colaborador.getId(), multiplicadorPuntos);
        }
    }


}
