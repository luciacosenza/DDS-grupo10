package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log
public class ContribucionService {

    private final ContribucionRepository contribucionRepository;
    protected final ColaboradorService colaboradorService;

    protected ContribucionService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService) {
        contribucionRepository = vContribucionRepository;
        colaboradorService = vColaboradorService;
    }

    public Contribucion obtenerContribucion(Long contribucionId) {
        return contribucionRepository.findById(contribucionId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public Contribucion guardarContribucion(Contribucion contribucion) {
        return contribucionRepository.save(contribucion);
    }

    // Para los tres métodos siguientes, los pusimos vacíos porque, si poníamos la clase como abstracta, había problemas para hacer el Autowired, y si no la poníamos abstracta (para que sea concreta y pueda usarse con @Qualifier) los métodos no podían ser abstractos

    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}

    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {}

    protected void calcularPuntos() {}

    public void confirmar(Long contribucionId, Long colaboradorId, LocalDateTime vFechaContribucion) {
        Contribucion contribucion = obtenerContribucion(contribucionId);

        // Podemos agregar lógica para confirmar que la Contribución fue efectivamente realizada

        contribucion.marcarComoCompletada();
        contribucion.setFechaContribucion(vFechaContribucion);   // Actualizo la fecha de contribución al momento que se confirma la realización de la Contribución
    }
}
