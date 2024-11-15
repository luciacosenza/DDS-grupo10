package com.tp_anual.proyecto_heladeras_solidarias.controller.rest;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.ubicador.UbicadorHeladera;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UbicadorController {

    private final UbicadorHeladera ubicadorHeladeraService;

    public UbicadorController(UbicadorHeladera vUbicadorHeladeraService) {
        this.ubicadorHeladeraService = vUbicadorHeladeraService;
    }

    @GetMapping("/ubicador-api/{latitud}/{longitud}")
    public List<Heladera> obtenerHeladerasCercanasA(@PathVariable Double latitud , @PathVariable Double longitud ) {
        return ubicadorHeladeraService.obtenerHeladerasCercanasAUbicacion(latitud, longitud);
    }
}
