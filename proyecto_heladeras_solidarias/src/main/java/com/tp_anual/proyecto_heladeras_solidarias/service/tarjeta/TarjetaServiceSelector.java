package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.Tarjeta;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log
public class TarjetaServiceSelector {

    private final Map<Class<? extends Tarjeta>, TarjetaService> tarjetaServiceMap = new HashMap<>();

    public TarjetaServiceSelector(TarjetaColaboradorService vTarjetaColaboradorService, TarjetaPersonaEnSituacionVulnerableService vTarjetaPersonaEnSituacionVulnerableService) {
        tarjetaServiceMap.put(TarjetaColaborador.class, vTarjetaColaboradorService);
        tarjetaServiceMap.put(TarjetaPersonaEnSituacionVulnerable.class, vTarjetaPersonaEnSituacionVulnerableService);
    }

    public TarjetaService obtenerTarjetaService(Class<? extends Tarjeta> tarjetaClass) {
        return tarjetaServiceMap.get(tarjetaClass);
    }
}
