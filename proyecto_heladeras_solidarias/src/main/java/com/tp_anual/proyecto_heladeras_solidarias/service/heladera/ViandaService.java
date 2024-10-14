package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraNula;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
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

    // Este método debe ser llamado cuando una Vianda es retirada de una Heladera (se queda "sin Heladera", momentáneamente)
    public void quitarDeHeladera(Long viandaId) {
        Vianda vianda = obtenerVianda(viandaId);
        vianda.quitarDeHeladera();
        guardarVianda(vianda);
    }

    // Este método debe ser llamado tanto cuando se entregó una Vianda de Donación o cuando se depositó una Vianda como parte de lote de una Distribución de Viandas
    public void marcarEntrega(Long viandaId) {
        Vianda vianda = obtenerVianda(viandaId);
        vianda.marcarEntrega();
        guardarVianda(vianda);
    }

    // Este método, al igual que "quitarDeHeladera()", debe ser llamado cuando una Vianda es retirada de una Heladera (está "sin entregar", momentáneamente)
    public void desmarcarEntrega(Long viandaId) {
        Vianda vianda = obtenerVianda(viandaId);
        vianda.desmarcarEntrega();
        guardarVianda(vianda);
    }
}
