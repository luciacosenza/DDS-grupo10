package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.CargaOferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.CargaOfertaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log
public class CargaOfertaService extends ContribucionService {

    private final CargaOfertaRepository cargaOfertaRepository;

    private final I18nService i18nService;

    public CargaOfertaService(CargaOfertaRepository vCargaOfertaRepository, I18nService vI18nService) {
        super();
        cargaOfertaRepository = vCargaOfertaRepository;

        i18nService = vI18nService;
    }

    @Override
    public CargaOferta obtenerContribucion(Long cargaOfertaId) {
        return cargaOfertaRepository.findById(cargaOfertaId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }

    @Override
    public CargaOferta guardarContribucion(Contribucion cargaOferta) {
        return cargaOfertaRepository.saveAndFlush((CargaOferta) cargaOferta);
    }

    @Override
    public void validarIdentidad(Long cargaOfertaId, Colaborador colaborador) {}   // Esta Contribución tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long cargaOfertaId, Colaborador colaborador, Double puntosSumados) {} // Esta Contribución no entra entre las que suman puntos

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    protected void calcularPuntos() {}  // Esta Contribución no entra entre las que suman puntos
}
