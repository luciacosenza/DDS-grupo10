package com.tp_anual.proyecto_heladeras_solidarias.service.i18n;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class I18nService {

    private final MessageSource messageSource;

    public I18nService(MessageSource vMessageSource) {
        messageSource = vMessageSource;
    }

    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }

    public String getMessage(String code) {
        return getMessage(code, Locale.getDefault(), (Object[]) null);
    }

    // Sobrecarga para permitir Locale personalizado
    public String getMessage(String code, Locale locale, Object... args) {
        return messageSource.getMessage(code, args, locale);
    }
}