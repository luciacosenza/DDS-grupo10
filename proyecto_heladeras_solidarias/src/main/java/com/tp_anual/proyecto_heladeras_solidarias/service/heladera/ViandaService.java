package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.ViandaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class ViandaService {

    private final ViandaRepository viandaRepository;

    public ViandaService(ViandaRepository vViandaRepository) {
        viandaRepository = vViandaRepository;
    }

    public Vianda obtenerVianda(Long viandaId) {
       return viandaRepository.findById(viandaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public Vianda guardarVianda(Vianda vianda) {
        return viandaRepository.save(vianda);
    }

    // Este método debe ser llamado cuando se depositó una Vianda como parte de lote de una Distribución de Viandas
    public void agregarAHeladera(Long viandaId, Heladera heladera) {
        Vianda vianda = obtenerVianda(viandaId);
        vianda.agregarAHeladera(heladera);
        guardarVianda(vianda);
    }

    // Este método debe ser llamado cuando se entregó una Vianda de Donación
    public void agregarAHeladeraPrimeraVez(Long viandaId, Heladera heladera) {
        Vianda vianda = obtenerVianda(viandaId);
        vianda.agregarAHeladeraPrimeraVez(heladera);
        guardarVianda(vianda);
    }

    // Este método debe ser llamado cuando una Vianda es retirada de una Heladera (se queda "sin Heladera", momentáneamente)
    public void quitarDeHeladera(Long viandaId) {
        Vianda vianda = obtenerVianda(viandaId);
        vianda.quitarDeHeladera();
        guardarVianda(vianda);
    }
}
