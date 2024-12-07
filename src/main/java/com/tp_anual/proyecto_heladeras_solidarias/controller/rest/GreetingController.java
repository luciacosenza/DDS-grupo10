package com.tp_anual.proyecto_heladeras_solidarias.controller.rest;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final I18nService i18nService;

    public GreetingController(I18nService vI18nService) {
        i18nService = vI18nService;
    }
}
