package com.tp_anual.proyecto_heladeras_solidarias.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static final ResourceBundle messages;

    // Inicializo el ResourceBundle sólo una vez
    static {
        Locale locale = Locale.getDefault();    // Uso por default el Locale del Sistema
        messages = ResourceBundle.getBundle("messages", locale);
    }

    // Método para obtener mensajes localizados
    public static String getMessage(String key, Object... args) {
        String pattern = messages.getString(key);
        MessageFormat messageFormat = new MessageFormat(pattern, Locale.getDefault());
        
        return messageFormat.format(args);
    }

    // Sobrecarga para permitir Locale personalizado
    public static String getMessage(String key, Locale locale, Object... args) {
        ResourceBundle customMessages = ResourceBundle.getBundle("messages", locale);
        String pattern = customMessages.getString(key);
        MessageFormat messageFormat = new MessageFormat(pattern, locale);
        
        return messageFormat.format(args);
    }
}
