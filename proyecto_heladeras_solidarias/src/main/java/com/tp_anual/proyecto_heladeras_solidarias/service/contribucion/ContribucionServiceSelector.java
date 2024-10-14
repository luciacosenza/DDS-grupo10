package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.*;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log
public class ContribucionServiceSelector {

    private final Map<Class<? extends Contribucion>, ContribucionService> contribucionserviceMap = new HashMap<>();

    public ContribucionServiceSelector(CargaOfertaService vCargaOfertaService, DistribucionViandasService vDistribucionViandasService, DonacionDineroService vDonacionDineroService, DonacionViandaService vDonacionViandaService, HacerseCargoDeHeladeraService vHacerseCargoDeHeladeraService, RegistroDePersonaEnSituacionVulnerableService vRegistroDePersonaEnSituacionVulnerableService) {
        contribucionserviceMap.put(CargaOferta.class, vCargaOfertaService);
        contribucionserviceMap.put(DistribucionViandas.class, vDistribucionViandasService);
        contribucionserviceMap.put(DonacionDinero.class, vDonacionDineroService);
        contribucionserviceMap.put(DonacionVianda.class, vDonacionViandaService);
        contribucionserviceMap.put(HacerseCargoDeHeladera.class, vHacerseCargoDeHeladeraService);
        contribucionserviceMap.put(RegistroDePersonaEnSituacionVulnerable.class, vRegistroDePersonaEnSituacionVulnerableService);
    }

    public ContribucionService obtenerContribucionService(Class<? extends Contribucion> contribucionClass) {
        return contribucionserviceMap.get(contribucionClass);
    }
}
