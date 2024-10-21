package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.HacerseCargoDeHeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorPuntosService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
@Log
public class HacerseCargoDeHeladeraService extends ContribucionService {

    private final HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository;
    private final ColaboradorPuntosService colaboradorPuntosService;
    private final Double multiplicadorPuntos = 5d;

    public HacerseCargoDeHeladeraService(HacerseCargoDeHeladeraRepository vHacerseCargoDeHeladeraRepository, ColaboradorPuntosService vColaboradorPuntosService) {
        super();
        hacerseCargoDeHeladeraRepository = vHacerseCargoDeHeladeraRepository;
        colaboradorPuntosService = vColaboradorPuntosService;
    }

    @Override
    public HacerseCargoDeHeladera obtenerContribucion(Long hacerseCargoDeHeladeraId) {
        return hacerseCargoDeHeladeraRepository.findById(hacerseCargoDeHeladeraId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public List<HacerseCargoDeHeladera> obtenerHacerseCargoDeHeladerasQueSumanPuntos() {
        return new ArrayList<>(hacerseCargoDeHeladeraRepository.findHacerseCargoDeHeladeras());
    }

    @Override
    public HacerseCargoDeHeladera guardarContribucion(Contribucion hacerseCargoDeHeladera) {
        return hacerseCargoDeHeladeraRepository.saveAndFlush((HacerseCargoDeHeladera) hacerseCargoDeHeladera);
    }

    @Override
    public void validarIdentidad(Long hacerseCargoDeHeladeraId, Colaborador colaborador) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long hacerseCargoDeHeladeraId, Colaborador colaborador, Double puntosSumados) {
        log.log(Level.INFO, I18n.getMessage("contribucion.HacerseCargoDeHeladera.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    protected void calcularPuntos() {
        List<HacerseCargoDeHeladera> hacerseCargoDeLaHeladeras = obtenerHacerseCargoDeHeladerasQueSumanPuntos();

        for (HacerseCargoDeHeladera hacerseCargoDeHeladera : hacerseCargoDeLaHeladeras ) {
            ColaboradorJuridico colaborador = (ColaboradorJuridico) hacerseCargoDeHeladera.getColaborador();
            colaboradorPuntosService.sumarPuntos(colaborador.getId(), multiplicadorPuntos);

            hacerseCargoDeHeladera.sumoPuntos();
            guardarContribucion(hacerseCargoDeHeladera);    // Al guardar la contribución, se guarda el colaborador por cascada (aunque ya había sido guardado)
            confirmarSumaPuntos(hacerseCargoDeHeladera.getId(), colaborador, multiplicadorPuntos);
        }
    }


}
