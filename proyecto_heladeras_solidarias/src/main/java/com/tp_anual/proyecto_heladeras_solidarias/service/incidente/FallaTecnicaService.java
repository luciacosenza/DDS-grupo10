package com.tp_anual.proyecto_heladeras_solidarias.service.incidente;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.FallaTecnica;
import com.tp_anual.proyecto_heladeras_solidarias.repository.incidente.FallaTecnicaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.notificador.NotificadorDeIncidentes;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class FallaTecnicaService {

    private final FallaTecnicaRepository fallaTecnicaRepository;
    private final NotificadorDeIncidentes notificadorDeIncidentes;

    public FallaTecnicaService(FallaTecnicaRepository vFallaTecnicaRepository, NotificadorDeIncidentes vNotificadorDeIncidentes) {
        fallaTecnicaRepository = vFallaTecnicaRepository;
        notificadorDeIncidentes = vNotificadorDeIncidentes;
    }

    public FallaTecnica obtenerFallaTecnica(Long idFallaTecnica) {
        return fallaTecnicaRepository.findById(idFallaTecnica).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<FallaTecnica> obtenerFallasTecnicas() {
        return new ArrayList<>(fallaTecnicaRepository.findAll());
    }

    public FallaTecnica guardarFallaTecnica(FallaTecnica fallaTecnica) {
        return fallaTecnicaRepository.save(fallaTecnica);
    }

    public void producirFallaTecnica(LocalDateTime fecha, Heladera heladera, Colaborador colaborador, String descripcion, String foto) {
        FallaTecnica fallaTecnica = new FallaTecnica(fecha, heladera, colaborador, descripcion, foto);
        guardarFallaTecnica(fallaTecnica);

        notificadorDeIncidentes.notificarIncidente(fallaTecnica);

        log.log(Level.INFO, I18n.getMessage("incidente.FallaTecnica.producirFallaTecnica_info", heladera.getNombre()));
    }
}
