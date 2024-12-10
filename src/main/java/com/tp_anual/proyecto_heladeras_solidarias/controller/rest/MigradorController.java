package com.tp_anual.proyecto_heladeras_solidarias.controller.rest;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.service.migrador.Migrador;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class MigradorController {

    private final Migrador migrador;

    private final I18nService i18nService;

    public MigradorController(Migrador vMigrador, I18nService vI18nService) {
        migrador = vMigrador;
        i18nService = vI18nService;
    }

    @PostMapping("/cargar-archivo")
    public String cargarArchivo(@RequestParam("file") MultipartFile file) {
        try {
            migrador.migrar(file, true);

            return i18nService.getMessage("controller.MigradorController.cargarArchivo");

        } catch (Exception e) {
            return i18nService.getMessage("controller.MigradorController.cargarArchivo_err");
        }
    }
}
